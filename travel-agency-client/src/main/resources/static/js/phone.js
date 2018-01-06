'use strict';

var serverHostname = "localhost";

var constraints = window.constraints = {
    audio: true,
    video: false
};

var AudioContext = window.AudioContext || window.webkitAudioContext
var context = new AudioContext()
var ws;

function handleSuccess(stream) {
    startRinging();
    displayPhoneControlsAndHideCallButton();
    var audioTracks = stream.getAudioTracks();
    stream.oninactive = function() {
        console.log('Stream ended');
    };
    connectToVapiAndStream(stream);
}

function connectToVapiAndStream(stream) {
	ws = new WebSocket("ws://".concat(serverHostname, ":8002/browser"));
    ws.binaryType = 'arraybuffer'
    send(stream, ws);
    ws.onmessage = play;
}

function handleError(error) {
    console.log('navigator.getUserMedia error: ', error);
}

function stream(){
    triggerCall();

}

function triggerCall(){
    $.post("http://".concat(serverHostname, ":8001/voice/calls/agent"));
    navigator.mediaDevices.getUserMedia(constraints).
            then(handleSuccess).catch(handleError);
}



function send(stream, ws){
    var source = context.createMediaStreamSource(stream);
    var processor = context.createScriptProcessor(1024, 1, 1);
    var downsampled = new Int16Array(2048);
    var downsample_offset = 0;
    function process_samples(){
      while(downsample_offset > 320) {
        var output = downsampled.slice(0, 320);
        downsampled.copyWithin(0, 320);
        downsample_offset -= 320;
        if(ws.readyState == ws.OPEN) {
          ws.send(output.buffer);
        }
      }
    }
    var sampleRatio = context.sampleRate / 16000
    processor.onaudioprocess = (audioProcessingEvent) => {
      var inputBuffer = audioProcessingEvent.inputBuffer;
      var outputBuffer = audioProcessingEvent.outputBuffer;
      var inputData = inputBuffer.getChannelData(0);
      var outputData = outputBuffer.getChannelData(0);
      for (var i = 0; i < inputData.length; i += sampleRatio) {
        var sidx = Math.floor(i);
        var tidx = Math.floor(i/sampleRatio);
        downsampled[downsample_offset + tidx] = inputData[sidx] * 32767;
      }
      downsample_offset += ~~(inputData.length/sampleRatio);
      if(downsample_offset > 320) {
        process_samples();
      }
      for (var sample = 0; sample < inputBuffer.length; sample++) {
        outputData[sample] = 0;
      }
    }
    source.connect(processor);
    processor.connect(context.destination);
}

function play(event){
    stopRinging();
    var time = 0
    time = Math.max(context.currentTime, time)
    var input = new Int16Array(event.data)
    if(input.length) {
      var buffer = context.createBuffer(1, input.length, 16000)
      var data = buffer.getChannelData(0)
      for (var i = 0; i < data.length; i++) {
        data[i] = input[i] / 32767
      }
      var source = context.createBufferSource()
      source.buffer = buffer
      source.connect(context.destination)
      source.start(time += buffer.duration)
    }
}

function startRinging(){
    document.getElementById('audio-player').play();
}

function stopRinging(){
    document.getElementById('audio-player').pause();
    document.getElementById('audio-player').currentTime = 0;
}

function hidePhoneControlsAndDisplayCallButton(){
    document.getElementById("phone").style.display = "none";
    document.getElementById("call-button").style.display = "inline";
}

function displayPhoneControlsAndHideCallButton(){
    document.getElementById("phone").style.display = "inline";
    document.getElementById("call-button").style.display = "none";
}

function closeWebSocket(){
    ws.close();
    stopRinging();
    hidePhoneControlsAndDisplayCallButton();
}
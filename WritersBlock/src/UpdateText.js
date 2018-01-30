/**
 * Created by Rachel on 3/3/2017.
 */


function onInput() {

    textArea = document.getElementById("mainText");
    return textArea.value;
}
function getSelStart(){
    textArea = document.getElementById("mainText");
    return textArea.selectionStart;
}
function getSelEnd(){
    textArea = document.getElementById("mainText");
    return textArea.selectionEnd;
}
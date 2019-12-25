var way = document.getElementById('way')
var wayInput = document.getElementById('way-input')
var textInput = document.getElementById('text-input')
var link = document.getElementById('link')
var changeTab = function () {
    link.style.display = 'none'
    if (way.value == 3 || way.value == 4) {
        wayInput.style.display = 'block'
    } else {
        wayInput.style.display = 'none'
    }
    if (way.value == 3) {
        textInput.innerText = '请填写手机号'
    } else if (way.value == 4) {
        textInput.innerText = '请填写邮箱地址'
    }
}
var submit = function () {
    link.style.display = 'block'
}
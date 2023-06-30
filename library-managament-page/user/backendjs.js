axios.defaults.baseURL = 'http://lzp123.nat300.top';

function showMenu(nid) {
    var header = document.getElementById(nid);
    var item_list = header.parentElement.parentElement.children;

    for (var i = 0; i < 3; i++) {
        var item = item_list[i];
        item.children[1].classList.add("hid");
    }

    document.getElementById(nid).classList.remove("hid");
}

let page = document.querySelector('#page')

let acs = document.querySelectorAll('.ac')

for(let i = 0; i < acs.length; i++) {
    let ac = acs[i];

    if(i === 0) {
        acs[i].classList.add('bg')
    }

    // console.log(ac);
    ac.onclick = function() {
        for(let j = 0; j < acs.length; j++) {
            let ac1 = acs[j];
            if(ac1.classList.contains('bg')) {
                ac1.classList.remove('bg');
            }
        }
        ac.classList.add('bg')
    }
}

let pre = document.querySelector('.pre')
pre.onclick = function() {
    window.location.href = 'http://lzp456.natapp1.cc/index/index.html'
}

let message = document.querySelector('.message')
let info = document.querySelector('.info')
let cm = document.querySelector('.closemessage')
let m2 = document.querySelector('.m2')
let users = document.querySelector('.users')
let dialog = document.querySelector('.dialog')
let user = document.querySelector('.u')
let area = document.querySelector('.area')
let allUserM = {
    client: new Array(),
    server: new Array(),
    messages: new Array(),
    read: new Array()
}
let flag = false

user.textContent = '用户:' + localStorage.getItem('user')
m2.style.backgroundColor = 'red'

message.onclick = function() {
    area.classList.add('blur')
    info.classList.remove('ds')    
    for(let i = 0; i < allUserM.length; i++) {
        if(allUserM[i].read === false) {
            flag = true
        }
    }
    if(!flag) {
        m2.classList.add('ds')
    } else {
        m2.classList.remove('ds')
        m2.style.backgroundColor = 'red'
    }
}

cm.onclick = function() {

    info.classList.add('ds')
    area.classList.remove('blur')
    for(let i = 0; i < allUserM.length; i++) {
        if(allUserM[i].read === false) {
            flag = true
        }
    }
    if(!flag) {
        m2.classList.add('ds')
    } else {
        m2.classList.remove('ds')
        m2.style.backgroundColor = 'red'
    }
}


function getAllUserMessages() {
    axios.get('/message/alluser/message')
    .then(res => {
        console.log(res.data.data)
        let ms = res.data.data
        let flag = false
        console.log(ms)
        for(let i = 0; i < ms.length; i++) {
            if(ms[i].read === false) {
                flag = true
            }
        }
        if(!flag) {
            m2.classList.add('ds')
        } else {
            m2.classList.remove('ds')
            m2.style.backgroundColor = 'red'
        }
        showUsers(res.data.data)
        // 给每个用户添加点击事件
        for(let i = 3; i < users.childNodes.length; i++) {
            users.childNodes[i].onclick = function() {
                for(let i = 3; i < users.childNodes.length; i++) {
                    users.childNodes[i].classList.remove('bgg')
                }
                users.childNodes[i].classList.add('bgg')
                dialog.innerHTML = ''
                // 解析用户信息
                for(let j = 0; j < ms.length; j++) {
                    if(users.childNodes[i].textContent === ms[j].client) {
                        for(let k = 0; k < ms[j].messages.length; k++) {
                            let message = users.childNodes[i].textContent + ': ' + ms[j].messages[k]
                            let l = document.createElement('li')
                            l.textContent = message
                            dialog.appendChild(l)
                        }
                    }
                }

                // 修改消息为已读
                axios.get('/message/read/' + users.childNodes[i].textContent)
                .then()
                .catch()
            }
        }
    })
    .catch(err => {
        console.log(err)
    })
}

function showUsers(data) {
    for(let i = 0; i < data.length; i++) {
        if(data[i].client === '123') {
            let userMessage = {
                client: data[i].client,
                server: data[i].server,
                messages: data[i].messages,
                read: data[i].read
            }
            allUserM.client.push(data[i].client)
            allUserM.server.push(data[i].server)
            allUserM.messages.push(data[i].messages)
            allUserM.read.push(data[i].read)
            let li = document.createElement('li')
            li.textContent = userMessage.client
            users.appendChild(li)
        }
    }
    console.log('alluserm=' + allUserM)
}

getAllUserMessages()
console.log(111)

// 发送消息
let t1 = document.querySelector('.t1')
t1.onkeyup = function(event) {
    let e = event || window.event
    if(e.key === 'Enter') {
        if(t1.value === '') {
            alert('消息不为空')
        } else {
            sendResMes()
        }
    }
}

let textsub = document.querySelector('.textsub')
textsub.onclick = function() {
    console.log(users.childNodes)
    if(t1.value === '') {
        alert('消息不为空')
    } else {
        sendResMes()
    }
}

function sendResMes() {
    for(let i = 3; i < users.childNodes.length; i++) {
        if(users.childNodes[i].classList.contains('bgg')) {
            let serverPhone = users.childNodes[i].textContent
            let responseMessage = t1.value
            let messageDTO = {
                client: localStorage.getItem('user'),
                server: serverPhone,
                message: responseMessage,
                read: 0
            }
            t1.value = ''
            axios({
                method: 'POST',
                url: '/message/responsemessage',
                data: messageDTO
            }).then(res => {
                t1.textContent = ''
                alert('成功发送')
            }).catch(err => {

            })
        }
    }
}
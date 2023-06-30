axios.defaults.baseURL = 'http://lzp123.nat300.top';

// 搜索
let search = document.querySelectorAll('.search')

for(let i = 0; i < search.length; i++) {
    search[i].onkeydown = function() {
        if(event.keyCode === 13) {
            let searchContent = search[i].value
            console.log(searchContent)
            axios.get('/book/search/' + searchContent)
            .then(res => {
                window.location.href = 'http://lzp456.natapp1.cc/index/search/search.html'
            })
            .catch(err => {
                console.log('查询失败')
                window.location.href = 'http://lzp456.natapp1.cc/index/index.html'
            })
        }
    }
}

// 后台管理
let managa = document.querySelector('#managa1')
managa.onclick = function() {
    axios.get('/user/auth')
    .then(res => {
        console.log(res.data.data)
        let auth = res.data.data

        if(auth === '管理员') {
            window.location.href = 'http://lzp456.natapp1.cc/managament/back-end.html'
        } else if(auth === '用户') {
            window.location.href = 'http://lzp456.natapp1.cc/user/back.html'
        } else if(auth === '未登录') {
            window.location.href = 'http://lzp456.natapp1.cc/sign-int-sign-up/index.html'
        }
    }).catch(err => {
        window.location.href = 'http://lzp456.natapp1.cc/sign-int-sign-up/index.html'
    }) 
}

axios.defaults.baseURL = 'http://lzp123.nat300.top';
let bt = document.querySelector('.bt')
let subemail = document.querySelector('.subemail')
let closeemail = document.querySelector('.closeemail')
let e1 = document.querySelector('.e1')
let email = document.querySelector('#email')
let tmp = {
    phone:'',
    email:''
}

closeemail.onclick = function() {
    email.classList.add('dp')
}

subemail.onclick = function() {
    tmp.email = e1.value
    console.log(tmp)
    axios({
        method:'POST',
        url: '/user/resetemail',
        data: tmp
    })
    .then(res => {
        alert('邮箱已修改')
        email.classList.add('dp')
        location.reload()
    })
    .catch((err) => console.log(err))
}

axios.get('user/alluser')
.then(res => {
    // console.log(res.data.data)
    showBook(res.data.data)
    let br = bt.childNodes
    // console.log(br)
    for(let i = 1; i < br.length; i++) {
        // console.log(br[i])
        let auth = br[i].childNodes[1].textContent
        let count = 0
        let phone = br[i].childNodes[0].textContent
        let resetEmail = br[i].childNodes[3].childNodes[0]
        let resetPwd = br[i].childNodes[3].childNodes[1]
        let deluser = br[i].childNodes[3].childNodes[2]
        console.log(resetPwd)
        console.log(deluser)
        let user = {
            phone : phone,
            email : '',
        }

        resetEmail.onclick = function() {
            tmp.phone = user.phone;
            email.classList.remove('dp')
        }

        resetPwd.onclick = function() {
            axios({
                method:'POST',
                url: '/user/resetpwd',
                data: user
            })
            .then(res => {
                alert('密码重置为123456')
                location.reload()
            })
            .catch((err) => console.log(err))
        }
        deluser.onclick = function() {

            axios({
                method:'POST',
                url: '/user/deluser',
                data: user
            })
            .then(res => {
                alert('成功删除')
                location.reload()
            })
            .catch((err) => console.log(err))
        }

    }
})
.catch(err => {
    console.log(err)
})

// 拼接每一页
showBook = function(arr) {
    let n = arr.length
    for(let i = 0; i < n; i++) {
        let data = arr[i];
        // console.log(data)
        let user = data.phone;
        let auth = data.auth;
        let email = data.email;
        let b = '';
        let tr = document.createElement('tr');
        b += '<td><p>' + user + '</p></td>'
        b += '<td><p>' + email + '</p></td>'
        b += '<td><p>'+ auth +'</p></td>'
        b += '<td><button>更改邮箱</button><button>重置密码</button><button>删除此用户</button></td>'
        tr.innerHTML = b
        bt.appendChild(tr)
        console.log(bt)
    }
}
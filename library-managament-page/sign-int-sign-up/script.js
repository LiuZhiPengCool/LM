axios.defaults.baseURL = 'http://lzp123.nat300.top'
axios.defaults.withCredentials = true

const signUpButton=document.getElementById('signUp')
const signInButton=document.getElementById('signIn')
const container=document.getElementById('container')
let realcode = document.querySelectorAll('.realcode')
// console.log(realcode)
let codeFromBack = ''
let flag = true
let phoneErr = document.querySelector('.phoneErr')
let pwErr = document.querySelector('.pwErr')
let codeErr = document.querySelector('.codeErr')

// 页面加载时获取验证码
for(let i = 0; i < realcode.length; i++) {
  realcode[i].onclick = function() {
    getCode()
  }
}

function getCode() {
  axios.get('/code').then((res) => {
    codeFromBack = res.data.data
    for(let i = 0; i < realcode.length; i++) {
      realcode[i].textContent = codeFromBack
    }
  }).catch((err) => {
    console.log(err)
  })
}

window.onload = function() {
  axios.get('/code').then((res) => {
    codeFromBack = res.data.data
    for(let i = 0; i < realcode.length; i++) {
      realcode[i].textContent = codeFromBack
    }
  }).catch((err) => {
    console.log(err)
  })
}

signUpButton.addEventListener('click',()=>{
  container.classList.add("right-panel-active")
})

signInButton.addEventListener('click',()=>{
  container.classList.remove("right-panel-active")
})

// 获取登陆表单
let user = []
let userPhone = document.querySelector('.user-phone')
let userPassword = document.querySelector('.user-password')
let lcode = document.querySelector('.lcode')
let lg = document.querySelector('.login')

// 手机号、密码、验证码失去焦点时间
userPhone.onblur = function phoneFun() {
  let phone = userPhone.value
  phoneErr.classList.add('ds')
  // 校验格式
  if(checkTelephone(phone) || phone === '123') {
    user[0] = phone
    userPhone.classList.remove('login-error')
    flag = true
  } else {
    phoneErr.classList.remove('ds')
    userPhone.classList.add('login-error')
    flag = false
  }
}

userPassword.onblur = function pwFun() {
  let pw = userPassword.value
  pwErr.classList.add('ds')
  console.log(pw)
  if(checkPW(pw)) {
    user[1] = pw
    userPassword.classList.remove('login-error')
    flag = true
  } else {
    pwErr.classList.remove('ds')
    userPassword.classList.add('login-error')
    flag = false
  }
}

lcode.onblur = function() {
  let cd = lcode.value
  console.log(cd)
  codeErr.classList.add('ds')
  if(cd != codeFromBack) {
    lcode.classList.remove('ds')
    lcode.classList.add('login-error')
    flag = false
  } else {
    lcode.classList.remove('login-error')
    flag = true
  }
  user.push(cd)
}

setInterval(() => {
  if(flag === false) {
    lg.classList.add('ban')
    lg.disabled = true
  } else {
    lg.classList.remove('ban')
    lg.disabled = false
  }
}, 100);

userPassword.onclick = function() {
  // alert(1)
}

lg.onclick = function() {
  user[0] = userPhone.value
  user[1] = userPassword.value
  user[2] = lcode.value
  let msg = ''
  // console.log(code)
  // console.log(user)
  let u = {
    phone: user[0],
    password: user[1],
    code: user[2]
  }

  console.log(u)
  axios({
      method: 'POST',
      url: '/user/login',
      data: u,
      xhrFiled: {
        withCredentials: true
      }
  }).then( res => {
      if(res.data.errorMsg === '用户不存在') {
        alert('用户不存在')
      } else {
        localStorage.setItem('user',res.data.data.phone)
        window.location.href= 'http://lzp456.natapp1.cc/index/index.html'
      }
    }
  )
  .catch((err) => {
    console.log(err)
  })
  return false
}

// 手机号验证
function checkPW(pw) {
  if(pw.toString === 123) {
    return true
  }

  let str = pw.toString()
  if(str.length >= 6 && str.length <= 8) {
    return true
  }
  return false
}

// 手机号验证
function checkTelephone(telephone) {
  var reg=/^[1][3,4,5,7,8][0-9]{9}$/;
  if (!reg.test(telephone)) {
      return false;
  } else {
      return true;
  }
}

// 注册操作
let sphone = document.querySelector('.sphone')
let semail = document.querySelector('.semail')
let spw = document.querySelector('.spw')
let spw2 = document.querySelector('.spw2')
let scode = document.querySelector('.scode')

// let signUp = document.querySelector('#signUp')
let loginUser = []

// 手机号、密码、验证码失去焦点时间
let sphoneErr = document.querySelector('.sphoneErr')
let semailErr = document.querySelector('.semailErr')
let spwErr = document.querySelector('.spwErr')

sphone.onblur = function phoneFun() {
  let phone = sphone.value
  sphoneErr.classList.add('ds')
  // 校验格式
  if(checkTelephone(phone)) {
    loginUser[0] = phone
    sphone.classList.remove('login-error')
    flag = true
  } else {
    sphoneErr.classList.remove('ds')
    sphone.classList.add('login-error')
    flag = false
  }
}

semail.onblur = function() {
  let email = semail.value
  semailErr.classList.add('ds')
  if(isEmail(email)) {
    loginUser[1] = email
    semail.classList.remove('login-error')
    flag = true
  } else {
    semailErr.classList.remove('ds')
    semail.classList.add('login-error')
    flag = false
  }
}

function isEmail(strEmail) {
  if (strEmail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
    return true;
  else
    return false
}

spw.onblur = function() {
  let pw = spw.value
  spwErr.classList.add('ds')
  if(checkPW(pw)) {
    spw.classList.remove('login-error')
    flag = true
  } else {
    spwErr.classList.remove('ds')
    spw.classList.add('login-error')
    flag = false
  }
}

spw2.onblur = function() {
  let pw = spw.value
  let pw1 = spw2.value
  spwErr.classList.add('ds')
  if(pw != pw1) {
    spwErr.classList.remove('ds')
  } else {
    loginUser[2] = pw1
  }
}

scode.onblur = function() {
  let cd = scode.value
  console.log(cd)
  codeErr.classList.add('ds')
  if(cd != codeFromBack) {
    scode.classList.remove('ds')
    scode.classList.add('login-error')
    flag = false
  } else {
    scode.classList.remove('login-error')
    flag = true
  }
}

// 注册
let signup = document.querySelector('.signup')
signup.onclick = function() {
  console.log(loginUser)
  u = {
    phone: loginUser[0],
    email: loginUser[1],
    password: loginUser[2]
  }

  axios({
    method: 'POST',
    url: '/user/sign',
    data: u,
  })

  setTimeout(() => {
    axios.get('/user/sign/check')
    .then(res => {
      console.log(res.data.data)
      let msg = res.data.data
      if(msg === '账号已存在') {
        alert(msg)
      } else {
        setTimeout(() => {
          window.location.href = 'http://lzp456.natapp1.cc/sign-int-sign-up/index.html'
        }, 2000);
        alert(msg + '确认后2秒钟跳转')
      }
    }).catch(err => {
      console.log(err)
    })
  }, 1000);
}
axios.defaults.baseURL = 'http://lzp123.nat300.top';

let articalNoSwift = {
    header:'',
    text: ''
}

let ih = document.querySelector('#ih')

function writeHeader() {
    articalNoSwift.header = ih.value
    console.log(articalNoSwift)
}

function mdConverter() {
    let cns = document.querySelector('#content-no-swift')
    let cs = document.querySelector('#content-swift')
    articalNoSwift.text = cns.value
    console.log(articalNoSwift)
    console.log(cns.value)
    let converter = new showdown.Converter();  //增加拓展table
    let view = converter.makeHtml(cns.value);
    cs.innerHTML = view
}

let pb = document.querySelector('#publishBtn')
pb.onclick = function() {
    axios({
        method:'POST',
        url: '/artical/save',
        data: articalNoSwift
    })
    .then(res => {
        alert('提交成功，等待管理员审核...')
        location.reload()
    })
    .catch((err) => console.log(err))
}
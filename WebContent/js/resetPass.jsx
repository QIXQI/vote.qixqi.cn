class ResetPassView extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {
            userId: this.getQueryString('userId')
        };
    }
    
    // 属性初始化器
    getQueryString = (name) => {
        let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        let urlSearch = decodeURIComponent(window.location.search);
        let r = urlSearch.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        };
        return null;
    }
    
    render(){
        if (this.state.userId === null || this.state.userId === undefined || this.state.userId === 'null'){
            return null;
        }
        return (
            <div style={{display: 'flex', width: '100%'}}>
                <UserInfoChoice userId={this.state.userId} />
                <UserPassView userId={this.state.userId} />
            </div>
        );
    }
}


class UserInfoChoice extends React.Component{
    constructor(props){
        super(props);
        
        this.toUserInfo = this.toUserInfo.bind(this);
        this.toResetPass = this.toResetPass.bind(this);
    }
    
    toUserInfo(){
        $(location).attr('href', 'userInfo.html');
    }
    
    toResetPass(){
        $(location).attr('href', 'resetPass.html?userId=' + this.props.userId);
    }
    
    componentDidMount(){
        // choice 悬浮事件
        $('.choice').hover(
            (event) => {
                $(event.target).css('background-color', '#f6f8fa');
            },
            (event) => {
                $(event.target).css('background-color', 'transparent');
            }
        );
    }
    
    render(){
        return(
            <div style={{display: 'inline-block', width: '35%'}}>
                <div style={{width: 220, height: 80, float: 'right', marginRight: 50, marginTop: 118, border: '1px solid #e1e4e8'}}>
                    <p className='choice' style={{height: 32, paddingLeft: 10, paddingTop: 8, color: '#1b1f23', cursor: 'pointer', userSelect: 'none', borderBottom: '1px solid #e1e4e8'}}
                        onClick={this.toUserInfo}>用户信息</p>
                    <p className='choice' style={{height: 32, paddingLeft: 10, paddingTop: 8, color: '#1b1f23', cursor: 'pointer', userSelect: 'none'}}
                        onClick={this.toResetPass}>更改密码</p>
                </div>
            </div>        
        );
    }
}


class UserPassView extends React.Component{
    constructor(props){
        super(props);
        
        this.handleBtnClick = this.handleBtnClick.bind(this);
    }
    
    handleBtnClick(){
        if (!this.check()){
            alert('密码格式有误，请严格按照格式填写！');
            return;
        }
        // 发送post请求，更改密码
        $.ajax({
            url: 'resetPass.do',
            type: 'POST',
            dataType: 'json',
            data: {
                userId: this.props.userId,
                oldPass: $('#oldPass').val(),
                newPass: $('#newPass').val()
            },
            async: true,
            success: function(message){
                if (message.result === 'success'){
                    this.userLogout();
                } else{
                    alert('重设密码失败：' + message.result);
                    console.error(message.result);
                }
            }.bind(this),
            error: function(err){
                alert('访问后台发生未知错误，请查看日志！');
                console.error(err.responseText);
            }.bind(this)
        });
    }
    
    userLogout(){
        // 发送post请求，注销
        $.ajax({
            url: 'logout.do',
            type: 'POST',
            dataType: 'json',
            data: {
                userId: this.props.userId
            },
            async: true,
            success: function(message){
                console.log(message);
                if (message.result === 'success'){
                    alert('更新密码成功，即将跳转到登录页面');
                    $(location).attr('href', 'login.html');
                } else{
                    alert('注销失败: ' + message.result);
                    console.error(message.result);
                }
            }.bind(this),
            error: function(err){
                alert('访问后台发生未知错误');
                console.error(err.responseText);
            }.bind(this)
        });
    }
    
    check(){
        if (this.checkPassword($('#oldPass').val()) && this.checkPassword($('#newPass').val()) && this.checkConfirmPass()){
            return true;
        }
        return false;
    }
    
    // 检测密码
    checkPassword(pass){
        var regNumber = /\d+/;          // 验证 0-9 的任意数字最少出现一次
        var regString = /[a-zA-Z]+/;    // 验证大小写字母任意字母最少出现一次

        if(pass.length >= 8 && regNumber.test(pass) && regString.test(pass)){
            return true;
        }
        return false;
    }
    
    checkConfirmPass(){
        if ($('#newPass').val() != $('#checkPass').val()){
            return false;
        }
        return true;
    }
    
    render(){
        return (
            <div style={{display: 'inline-block', width: '65%'}}>
                <div style={{height: 38, width: '80%', marginTop: 80, marginBottom: 20, borderBottom: '1px solid #e1e4e8'}}>
                    <h2 style={{fontFamily: '-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica,Arial,sans-serif,Apple Color Emoji,Segoe UI Emoji'}}>重设密码</h2>
                </div>
                <div style={{marginBottom: 50, display: 'inline-block', width: '35%'}}>
                    <label htmlFor='oldPass'>原先的密码</label><br />
                    <input type='password' id='oldPass' style={this.props.inputStyle} name='oldPass' tabindex='1' /><br />
                    <label htmlFor='newPass'>新的密码</label>
                    <label style={{fontSize: 10, marginLeft: 15}}>至少<span style={{color: 'rgb(219, 0, 24)'}}>8个字符，一个数字，<br /><span style={{marginLeft: 80}}>一个字母</span></span></label><br />
                    <input type='password' id='newPass' style={this.props.inputStyle} name='newPass' tabindex='2' /><br />
                    <label htmlFor='checkPass'>确认密码</label><br />
                    <input type='password' id='checkPass' style={this.props.inputStyle} name='checkPass' tabindex='3' /><br />
                    <p><button id='updatePass' onClick={this.handleBtnClick} style={this.props.updateBtn}>更新密码</button></p>
                </div>
            </div>        
        );
    }
}

UserPassView.defaultProps = {
    inputStyle: {
        marginTop: 10,
        marginBottom: 15,
        width: 200,
        height: 26,
        paddingLeft: 10,
        fontSize: 14,
        border: '1px solid rgb(206, 213, 218)',
        borderRadius: 3,
        backgroundColor: 'rgba(255, 255, 255, 0.1)'
    },
    updateBtn: {
        backgroundColor: '#2ea44f', 
        color: 'white', 
        borderColor: 'rgba(27,31,35,.15)', 
        padding: '5px 16px', 
        fontSize: 14, 
        outline: 'none', 
        cursor: 'pointer', 
        borderRadius: 6,
        marginTop: 5
    }
}


ReactDOM.render(
    <ResetPassView />,
    $('#resetPass').get(0)
);
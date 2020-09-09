class UserProfile extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {
            userInfo: {}
        };
        
        this.getLoginUser = this.getLoginUser.bind(this);
        this.setUserInfo = this.setUserInfo.bind(this);
    }
    
    getLoginUser(){
        $.ajax({
            url: 'getLoginUser.do',
            type: 'GET',
            dataType: 'json',
            async: true,
            success: function(message){
                console.log(message);
                if (message.result === 'success'){
                    this.setState({
                        userInfo: message.user
                    });
                }
            }.bind(this),
            error: function(err){
                alert('访问后台发生未知错误');
                console.error(err.responseText);
            }.bind(this)
        });
    }
    
    setAvatarUrl = (avatarUrl) => {
        var userInfo = this.state.userInfo;
        userInfo.userAvatar = avatarUrl;
        this.setState({
            userInfo: userInfo
        });
    };
    
    setUserInfo(){
        this.getLoginUser();
    }
    
    componentDidMount(){
        this.getLoginUser();
    }
    
    render(){
        if (this.state.userInfo.userId === null || this.state.userInfo.userId === undefined){
            return null;
        }
        return (
            <div style={{display: 'flex', width: '100%'}}>
                <UserInfoChoice userId={this.state.userInfo.userId} />
                <UserInformation userInfo={this.state.userInfo} setAvatarUrl={this.setAvatarUrl}
                    setUserInfo={this.setUserInfo} />
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


class UserAvatar extends React.Component{
    constructor(props){
        super(props);
        
        this.editAvatar = this.editAvatar.bind(this);
        this.uploadAvatar = this.uploadAvatar.bind(this);
    }
    
    editAvatar(){
        $('#uploadAvatar').click();
    }
    
    uploadAvatar(){
        var formData = new FormData();
        formData.append('userId', this.props.userId);
        formData.append('avatar', $('#uploadAvatar').get(0).files[0]);
        $.ajax({
            url: 'setUserAvatar.do',
            type: 'POST',
            async: true,
            dataType: 'json',
            data: formData,
            processData: false,     // 数据不作处理
            contentType: false,     // 不设置 content-type
            success: function(message){
                console.log(message);
                if (message.result === 'success'){
                    this.props.setAvatarUrl(message.avatarUrl);
                } else{
                    alert('上传头像失败：' + message.result);
                    console.error(message.result);
                    $('#uploadAvatar').val('');
                }
            }.bind(this),
            error: function(err){
                alert('上传头像失败，请查看日志');
                console.error(err.responseText);
                $('#uploadAvatar').val('');
            }.bind(this)
        });
    }
    
    componentDidMount(){
        $('#uploadAvatar').change((event) => {
            if ($('#uploadAvatar').get(0).files.length !== 0){
                this.uploadAvatar();
            }
        });
    }
    
    componentDidUpdate(){
        // 加时间戳 或 随机数，有时仍会出现错误
        var avatarUrl = this.props.avatar + '?t=' + Math.random();
        $('#userAvatar').attr('src', avatarUrl);
    }
    
    render(){
        return (
            <div style={{display: 'inline-block', width: '35%'}}>
                <input type='file' id='uploadAvatar' name='uploadAvatar' accept='image/*' style={{display: 'none'}} />
                <div style={this.props.imgDivStyle}>
                    <img id='userAvatar' src={this.props.avatar} style={this.props.avatarStyle} />
                </div>
                <div style={{textAlign: 'left', display: 'block', marginLeft: 40, marginTop: 20}}>
                    <button style={this.props.btnStyle} onClick={this.editAvatar}>
                        <img src='images/edit.png' style={{width: 25, height: 25, marginRight: 8, display: 'inline-block', verticalAlign: 'middle'}} />
                        <span style={{lineHeight: '40px'}}>更改头像</span>
                    </button>
                </div>
            </div>        
        );
    }
}

UserAvatar.defaultProps = {
    imgDivStyle: {
        textAlign: 'left',
        display: 'block',
        marginLeft: 0,
        marginTop: 30
    },
    avatarStyle: {
        width: 200,
        height: 200,
        borderRadius: '50%'
    },
    btnStyle: {
        backgroundColor: '#24292e',
        width: 120,
        borderRadius: 6,
        outline: 'none',
        paddingTop: 4,
        paddingBottom: 4,
        paddingLeft: 8,
        paddingRight: 8,
        color: 'white',
        cursor: 'pointer'
    }
};


class UserInformation extends React.Component{
    constructor(props){
        super(props);
        
        this.handleEditClick = this.handleEditClick.bind(this);
    }
    
    handleEditClick(){
        this.setUserInfo();
    }
    
    setUserInfo(){
        alert($('#userSex').val());
        var data = {
            userId: this.props.userInfo.userId,
            userName: $('#userName').val(),
            userSex: $('#userSex').val(),
            userEmail: $('#userEmail').val(),
            userPhone: $('#userPhone').val(),
            userBirthday: $('#userBirthday').val()
        };
        // 发送post请求
        $.ajax({
            url: 'setUserInfo.do',
            type: 'POST',
            dataType: 'json',
            data: data,
            async: true,
            success: function(message){
                if (message.result === 'success'){
                    // 回调给主组件，重新请求数据
                    this.props.setUserInfo();
                } else{
                    alert('更新个人信息错误：' + message.result);
                    console.error(message.result);
                }
            }.bind(this),
            error: function(err){
                alert('访问后台出现未知错误，请查看日志');
                console.error(err.responseText);
            }.bind(this)
        });
    }
    
    componentDidMount(){
        /* 按钮悬浮样式 */
        $('#updateInfo').hover(
            (event) => {
                $(event.target).css('background-color', '#2c974b');
            },
            (event) => {
                $(event.target).css('background-color', '#2ea44f');
            }
        );
    }
    
    render(){
        return (
            <div style={{display: 'inline-block', width: '65%'}}>
                <div style={{height: 38, width: '80%', marginTop: 80, marginBottom: 20, borderBottom: '1px solid #e1e4e8'}}>
                    <h2 style={{fontFamily: '-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica,Arial,sans-serif,Apple Color Emoji,Segoe UI Emoji'}}>用户信息</h2>
                </div>
                <div style={{display: 'flex'}}>
                    <div className='userInformation' style={{marginBottom: 50, display: 'inline-block', width: '35%'}}>
                        <label htmlFor='userName'>用户名</label><br />
                        <input type='text' id='userName' name='userName' style={this.props.inputStyle} defaultValue={this.props.userInfo.userName} tabindex='1' /><br />
                        <label htmlFor='userSex'>性别</label><br />
                        <select id='userSex' name='userSex' tabindex='2' defaultValue={this.props.userInfo.userSex} style={this.props.selectStyle}>
                            <option value='male'>男</option>
                            <option value='female'>女</option>
                        </select><br />
                        <label htmlFor='userEmail'>邮箱</label><br />
                        <input type='email' id='userEmail' name='userEmail' style={this.props.inputStyle} defaultValue={this.props.userInfo.userEmail} tabindex='3' /><br />
                        <label htmlFor='userPhone'>手机号</label><br />
                        <input type='text' id='userPhone' name='userPhone' style={this.props.inputStyle} defaultValue={this.props.userInfo.userPhone} tabindex='4' /><br />
                        <label htmlFor='userBirthday'>生日</label><br />
                        <input type='text' id='userBirthday' name='userBirthday' style={this.props.inputStyle} defaultValue={this.props.userInfo.userBirthday} tabindex='5' /><br />
                        <label htmlFor='userJoinTime'>加入时间</label><br />
                        <input type='text' id='userJoinTime' name='userJoinTime' disabled='disabled' style={this.props.inputStyle} value={this.createTime(this.props.userInfo.userJoinTime)} tabindex='6' /><br />
                        <label htmlFor='userLoginTime'>上次登录时间</label><br />
                        <input type='text' id='userLoginTime' name='userLoginTime' disabled='disabled' style={this.props.inputStyle} value={this.createTime(this.props.userInfo.userLoginTime)} tabindex='7' /><br />
                        <p><button id='updateInfo' style={this.props.updateBtn} onClick={this.handleEditClick}>更新用户信息</button></p>
                    </div>
                    <UserAvatar avatar={this.props.userInfo.userAvatar} userId={this.props.userInfo.userId} 
                        setAvatarUrl={this.props.setAvatarUrl} />
                </div>
            </div>
        );
    }


    //时间戳转换
    createTime = (v) => {
        var date = new Date(v);
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        m = m<10?'0'+m:m;

        var d = date.getDate();
        d = d<10?("0"+d):d;

        var h = date.getHours();
        h = h<10?("0"+h):h;

        var M = date.getMinutes();
        M = M<10?("0"+M):M;

        var oHour = date.getHours()
        oHour = oHour<10?("0"+oHour):oHour;

        var oMin = date.getMinutes()
        oMin = oMin<10?("0"+oMin):oMin;

        var oSen = date.getSeconds()
        oSen = oSen<10?("0"+oSen):oSen;

        var str = y+"-"+m+"-"+d+"  "+oHour+":"+oMin+":"+oSen;
        return str;
    };
}

UserInformation.defaultProps = {
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
    selectStyle: {
        marginTop: 10,
        marginBottom: 15,
        width: 214,
        height: 30,
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
    <UserProfile />,
    $('#userProfile').get(0)
);
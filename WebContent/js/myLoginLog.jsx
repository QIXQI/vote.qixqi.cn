class LoginLogView extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {};
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
                } else{
                    alert('获取用户信息失败：' + message.result);
                    console.error(message.result);
                }
            }.bind(this),
            error: function(err){
                alert('访问后台发生未知错误');
                console.error(err.responseText);
            }.bind(this)
        });
    }
    
    getLoginLogList(){
        $.ajax({
            url: 'getLoginLogs.do',
            type: 'GET',
            dataType: 'json',
            async: true,
            success: function(message){
                console.log(message);
                if (message.result === 'success'){
                    this.setState({
                        loginLogList: message.loginLogList
                    })
                } else{
                    alert('获取登录日志失败：' + message.result);
                    console.error(message.result);
                }
            }.bind(this),
            error: function(err){
                alert('访问后台出现异常，请查看日志！');
                console.error(err.responseText);
            }.bind(this)
        });
    }
    
    componentDidMount(){
        this.getLoginUser();
        this.getLoginLogList();
    }
    
    componentWillUnmount(){
        // 组件卸载前，停止没有完成的ajax请求
        this.serverRequest.abort();
    }
    
    render(){
        if (this.state.userInfo === null || this.state.userInfo === undefined){
            return null;
        }
        if (this.state.loginLogList === null || this.state.loginLogList === undefined){
            return null;
        }
        var loginLogItems = this.state.loginLogList.map((loginLog, index) => {
            return <LogItemView loginLog={loginLog} userInfo={this.state.userInfo} key={index} />
        });
        return (
            <div style={{display: 'flex', width: '100%'}}>
                <div style={{display: 'flex', width: '35%', flexDirection: 'column'}}></div>
                <div style={{display: 'block', width: '25%', marginTop: 60, marginBottom: 50}}>
                    <div style={{height: 30, textAlign: 'center', paddingBottom: 10, borderBottom: '1px solid #bbbbbb'}}>
                        <h3><a href='userInfo.html'>{this.state.userInfo.userName}</a> 的登录日志</h3>
                    </div>
                    <ul>{loginLogItems}</ul>
                </div>
            </div>
        );
    }
}

class LogItemView extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {};
    }
    
    render(){
        return (
            <li style={this.props.liStyle}>
                <div style={this.props.logItemDiv}>
                    <div style={{display: 'inline-block', width: 50}}>
                        <img src={this.props.userInfo.userAvatar} style={this.props.avatarStyle} 
                            alt={this.props.userInfo.userName} />
                    </div>
                    <div style={{display: 'inline-block'}}>
                        <label>{this.props.userInfo.userName}</label>
                        <label style={{marginLeft: 15, fontSize: 12, color: '#555', marginRight: 15}}>{this.props.loginLog.loginIp}</label><br />
                        <label style={{fontSize: 12}}>{this.createTime(this.props.loginLog.loginTime)}</label>
                    </div>
                </div>
            </li>
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

        var str = y+"-"+m+"-"+d+" "+oHour+":"+oMin+":"+oSen;
        return str;
    };
}

LogItemView.defaultProps = {
    liStyle: {
        display: 'flex',
        width: '100%',
        paddingTop: 12,
        paddingBottom: 12,
        borderBottom: '1px solid #e1e4e8'
    },
    logItemDiv: {
        display: 'inline-block',
        width: '100%'
    },
    avatarStyle: {
        width: 34,
        height: 34,
        borderRadius: 6
    }
};


ReactDOM.render(
    <LoginLogView />,
    $('#loginLogView').get(0)
);
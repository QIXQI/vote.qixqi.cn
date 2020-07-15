class VoteLogView extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {
            userInfo: {}
        };
        this.getLoginUser = this.getLoginUser.bind(this);
        this.getVoteLogList = this.getVoteLogList.bind(this);
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
    
    getVoteLogList(){
        $.ajax({
            url: 'getVoteLogsByUser.do',
            type: 'GET',
            dataType: 'json',
            async: true,
            success: function(message){
                console.log(message);
                if (message.result === 'success'){
                    this.setState({
                        voteLogList: message.voteLogList
                    });
                } else{
                    alert('获取 voteLogList 失败：' + message.result);
                    console.error(message.result);
                }
            }.bind(this),
            error: function(err){
                alert('访问后台发生未知错误');
                console.error(err.responseText);
            }.bind(this)
        });
    }
    
    componentDidMount(){
        this.getLoginUser();
        this.getVoteLogList();
    }
    
    componentWillUnmount(){
        // 组件卸载前，停止没有完成的ajax请求
        this.serverRequest.abort();
    }
    
    render(){
        if (this.state.userInfo.userId === null || this.state.userInfo.userId === undefined){
            return null;
        }
        if (this.state.voteLogList === null || this.state.voteLogList === undefined){
            return null;
        }
        var voteLogItems = this.state.voteLogList.map((voteLog, index) => {
            return <VoteLogItemView key={index} voteLog={voteLog} userInfo={this.state.userInfo} />
        });
        return (
            <div style={{display: 'flex', width: '100%'}}>
                <div style={{display: 'flex', width: '35%', flexDirection: 'column'}}></div>
                <div style={{display: 'block', width: '25%', marginTop: 60, marginBottom: 50}}>
                    <div style={{height: 30, textAlign: 'center', paddingBottom: 10, borderBottom: '1px solid #bbbbbb'}}>
                        <h3><a href='userInfo.html'>{this.state.userInfo.userName}</a> 的投票日志</h3>
                    </div>
                    <ul>{voteLogItems}</ul>
                </div>
            </div>
        );
    }
}


class VoteLogItemView extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {};
        this.getVoteInfo = this.getVoteInfo.bind(this);
    }
    
    getVoteInfo(){
        $.ajax({
            url: 'getVote.do',
            type: 'GET',
            dataType: 'json',
            data: {
                voteId: this.props.voteLog.voteId
            },
            async: true,
            success: function(message){
                console.log(message);
                if (message.result === 'success'){
                    this.setState({
                        vote: message.vote
                    });
                } else{
                    alert('获取投票' + this.props.voteLog.voteId + '的日志失败：' + message.result);
                    console.error(message.result);
                }
            }.bind(this),
            error: function(err){
                alert('访问后台出现未知错误');
                console.error(err.responseText);
            }.bind(this)
        });
    }
    
    componentDidMount(){
        this.getVoteInfo();
    }
    
    componentWillUnmount(){
        // 组件卸载前，停止没有完成的ajax请求
        this.serverRequest.abort();
    }
    
    render(){
        if (this.state.vote === undefined){
            return null;
        }
        const itemUrl = 'result.html?optionType=' + this.state.vote.voteType + '&voteId=' + this.state.vote.voteId;
        return (
            <li style={this.props.liStyle}>
                <div style={this.props.logItemDiv} className='logItemDiv'>
                    <div style={{display: 'inline-block', width: 50}}>
                        <img src={this.props.userInfo.userAvatar} style={this.props.avatarStyle} 
                            alt={this.props.userInfo.userName} />
                    </div>
                    <div style={{display: 'inline-block'}}>
                        <label>{this.props.userInfo.userName}</label>
                        <label style={{marginLeft: 15, fontSize: 12, color: '#aaa', marginRight: 15}}>参与了投票</label>
                        <a href={itemUrl}>{this.state.vote.voteName}</a><br />
                        <label style={{fontSize: 12}}>{this.createTime(this.props.voteLog.voteTime)}</label>
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

VoteLogItemView.defaultProps = {
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
}
        
ReactDOM.render(
    <VoteLogView />,
    $('#voteLogView').get(0)
);
class VoteResult extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {
            voteId: this.getQueryString('voteId'),
            optionType: this.getQueryString('optionType'),
            isBar: true
        };
        this.getOptionList = this.getOptionList.bind(this);
        this.getVote = this.getVote.bind(this);
        this.getVoteLogList = this.getVoteLogList.bind(this);
        this.handleChangeClick = this.handleChangeClick.bind(this);
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
    
    getVote(){
        // 发送 get请求，获取投票信息
        if (this.state.optionType == null || this.state.voteId == null){
            return;
        }
        $.ajax({
            url: 'getVote.do',
            type: 'GET',
            dataType: 'json',
            data: {
                voteId: this.state.voteId
            },
            async: true,
            success: function(message){
                if (message.result === 'success'){
                    console.log(message);
                    this.setState({
                        vote: message.vote
                    });
                } else{
                    alert('获取 vote 失败');
                    console.error(message.result);
                }
            }.bind(this),
            error: function(err){
                alert('访问后台出现未知错误');
                console.error(err.responseText);
            }.bind(this)
        });
    }
    
    getOptionList(){
        // 发送 get请求，获取选项列表
        if (this.state.optionType == null || this.state.voteId == null){
            alert('optionType 或 voteId 为空，无法获取optionList');
            return;
        }
        $.ajax({
            url: 'getOptions.do',
            type: 'GET',
            dataType: 'json',
            data: {
                optionType: this.state.optionType,
                voteId: this.state.voteId
            },
            async: true,
            success: function(message){
                if (message.result === 'success'){
                    console.log(message);
                    this.setState({
                        optionList: message.optionList
                    });
                } else{
                    alert('获取optionList失败');
                    console.error(message.result);
                }
            }.bind(this),
            error: function(err){
                alert('访问后台出现未知错误');
                console.error(err.responseText);
            }.bind(this)
        });
    }
    
    getVoteLogList(){
        // 发送 get请求，获取投票日志列表
        if (this.state.optionType == null || this.state.voteId == null){
            return;
        }
        $.ajax({
            url: 'getVoteLogsByVote.do',
            type: 'GET',
            dataType: 'json',
            data: {
                voteId: this.state.voteId
            },
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
                alert('访问后台出现未知错误');
                console.error(err.responseText);
            }.bind(this)
        });
    }
    
    handleChangeClick(){
        this.setState(state => ({
            isBar: !state.isBar
        }));
    }
    
    componentDidMount(){
        // ajax 异步获取数据
        this.getVote();
        this.getOptionList();
        this.getVoteLogList();
    }
    
    componentWillUnmount(){
        // 组件卸载前，停止没有完成的ajax请求
        this.serverRequest.abort();
    }
    
    render(){
        var optionDescs;
        var optionPolls;
        var optionDescPolls;
        if (this.state.optionList === undefined){
            optionDescs = [];
            optionPolls = [];
            optionDescPolls = [];
        } else{
            optionDescs = this.state.optionList.map((option) => {
                return option.optionDesc1;
            });
            optionPolls = this.state.optionList.map((option) => {
                return option.optionPoll;
            });
            optionDescPolls = this.state.optionList.map((option) => {
                return {
                    name: option.optionDesc1,
                    value: option.optionPoll
                };
            });
        }
        
        const VoteView = ( this.state.isBar ? 
                <VoteBar optionDescs={optionDescs} optionPolls={optionPolls} vote={this.state.vote} /> : 
                <VotePie optionDescPolls={optionDescPolls} vote={this.state.vote} /> );

        const btnVal = !this.state.isBar ? '饼状图' : '柱状图';
        
        return (
            <div style={{display: 'flex', width: '100%'}}>
                <div style={this.props.btnDivStyle}>
                    <div style={{textAlign: 'right', display: 'block', marginTop: 250, marginRight: 50}}>
                        <button onClick={this.handleChangeClick} style={this.props.btnStyle}>
                            <img src='images/change.png' alt={btnVal} title={btnVal} style={this.props.imgStyle} />
                            {btnVal}
                        </button>
                    </div>
                </div>
                {VoteView}
                <VoteLogView voteLogList={this.state.voteLogList} optionType={this.state.optionType} voteId={this.state.voteId} />
            </div>
        );
    }
}

VoteResult.defaultProps = {
    btnDivStyle: {
        display: 'flex',
        flexDirection: 'column',
        width: '28%'
    },
    btnStyle: {
        padding: '3px 12px',
        fontSize: 16,
        lineHeight: '50px',
        backgroundColor: 'transparent',
        position: 'relative',
        display: 'inline-block',
        fontWeight: 500,
        whiteSpace: 'nowrap',
        verticalAlign: 'middle',
        cursor: 'pointer',
        borderRadius: 12,
        outline: 'none'
    },
    imgStyle: {
        verticalAlign: 'middle',
        marginRight: 4,
        display: 'inline-block',
        width: 40,
        height: 40
    }
};


class VoteBar extends React.Component{
    constructor(props){
        super(props);
        
        this.chart = null;
        this.updateChart = this.updateChart.bind(this);
    }
    
    componentDidMount(){
        this.chart = echarts.init($('#voteBar').get(0));
        if (this.props.optionDescs.length > 0){
            this.updateChart();
        }
    }
    
    componentWillUnmount(){
        if (this.chart !== null){
            this.chart.dispose();       // 释放图表 
            this.chart = null;
        }
    }
    
    componentDidUpdate(){
        if (this.props.optionDescs.length > 0){
            this.updateChart();
        }
    }
    
    updateChart(){
        this.chart.setOption({
            title: {
                text: this.props.vote === undefined ? '' : this.props.vote.voteName,
                x: 60,
                y: 'top'
            },
            tooltip: {},
            legend: {
                data: ['vote']
            },
            xAxis: {
                data: this.props.optionDescs
            },
            yAxis: {},
            series: [{
                name: 'vote',
                type: 'bar',
                data: this.props.optionPolls
            }]
        });
    }
    
    render(){
        return (
            <div id='voteBar' style={{width: 500, height: 400, marginTop: 100, display: 'inline-block'}}></div>
        );
    }
}


class VotePie extends React.Component{
    constructor(props){
        super(props);
        
        this.chart = null;
        this.updateChart = this.updateChart.bind(this);
    }
    
    componentDidMount(){
        this.chart = echarts.init($('#votePie').get(0));
        if (this.props.optionDescPolls.length > 0){
            this.updateChart();
        }
    }
    
    componentWillUnmount(){
        if (this.chart !== null){
            this.chart.dispose();       // 释放图表 
            this.chart = null;
        }
    }
    
    componentDidUpdate(){
        if (this.props.optionDescPolls.length > 0){
            this.updateChart();
        }
    }
    
    updateChart(){
        this.chart.setOption({
            title: {
                text: this.props.vote === undefined ? '' : this.props.vote.voteName,
                x: 'center',
                y: 'top'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br />{b}: {c}票 ({d}%)'
            },
            series: {
                name: this.props.vote === undefined ? '' : this.props.vote.voteName,
                type: 'pie',
                roseType: 'angle',
                radius: '55%',
                data: this.props.optionDescPolls,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            formatter: '{b}: {c}票({d}%)'
                        },
                        labelLine: {
                            show: true
                        }
                    }
                }
            }
        });
    }
    
    render(){
        return (
            <div id='votePie' style={{width: 500, height: 400, marginTop: 100, display: 'inline-block'}}></div>
        );
    }
}


class VoteLogView extends React.Component{
    constructor(props){
        super(props);
        
        this.handleVoteClick = this.handleVoteClick.bind(this);
    }
    
    handleVoteClick(){
        if (this.props.optionType === null || this.props.voteId === null){
            alert('optionType 或 voteId 为空，无法跳转');
            return;
        }
        $(location).attr('href', 'addPoll.html?optionType=' + this.props.optionType + '&voteId=' + this.props.voteId);
    }
    
    componentDidMount(){
        $('#toVote').hover(
            (event) => {
                $('#toVote').find('label').eq(0).css({'color': '#fc6423', 'border-bottom': '1px solid #fc6423'});
            },
            (event) => {
                $('#toVote').find('label').eq(0).css({'color': '#0593d3', 'border-bottom': 'none'});
            }
        );
    }
    
    render(){
        var voteLogItems;
        if (this.props.voteLogList === undefined){
            voteLogItems = '';
        } else{
            voteLogItems = this.props.voteLogList.map((voteLog, index) => {
                return <VoteLogItemView key={index} voteLog={voteLog} />;
            });
        }
        
        return (
            <div style={this.props.divStyle}>
                <div style={{width: '100%', height: 70, display: 'block'}}>
                    <div style={{height: 30, textAlign: 'center', paddingTop: 5, borderBottom: '1px solid #bbbbbb'}}>投票日志</div>
                    <div id='toVote' onClick={this.handleVoteClick} style={{height: 30, textAlign: 'center', paddingTop: 5, cursor: 'pointer'}}>
                        <label style={{color: '#0593d3', cursor: 'pointer'}}>点击此处开始投票</label>
                    </div>
                </div>
                <ul>{voteLogItems}</ul>
            </div>        
        );
    }
}

VoteLogView.defaultProps = {
    divStyle: {
        display: 'block',
        width: '25%',
        marginLeft: 80,
        paddingTop: 60
    }
}


class VoteLogItemView extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {};
        
        this.getUserInfo = this.getUserInfo.bind(this);
    }
    
    getUserInfo(){
        $.ajax({
            url: 'getSimpleUser.do',
            type: 'GET',
            dataType: 'json',
            data: {
                userId: this.props.voteLog.userId
            },
            async: true,
            success: function(message){
                console.log(message);
                if (message.result === 'success'){
                    this.setState({
                        user: message.user
                    });
                } else{
                    alert('获取用户简要信息失败：' + message.result);
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
        this.getUserInfo();
    }
    
    componentWillUnmount(){
        // 组件卸载前，停止没有完成的ajax请求
        this.serverRequest.abort();
    }
    
    render(){
        if (this.state.user === undefined){
            return null;
        }
        return (
            <li style={this.props.liStyle}>
                <div style={this.props.logItemDiv} className='logItemDiv'>
                    <div style={{display: 'inline-block', width: 50}}>
                        <img src={this.state.user.userAvatar} style={this.props.avatarStyle} alt={this.state.user.userName} title={this.state.user.userName} />
                    </div>
                    <div style={{display: 'inline-block'}}>
                        <label>{this.state.user.userName}</label> <br />
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
    <VoteResult />,
    $('#voteResult').get(0)
);
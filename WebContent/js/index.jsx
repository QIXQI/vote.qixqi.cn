class VoteView extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {
            isFirst: true       // 是否是第一次渲染                
        };
        this.getVoteList = this.getVoteList.bind(this);
    }
    
    getVoteList(){
        // 发送get请求，获取voteList
        $.ajax({
            url: 'getAllVotes.do',
            type: 'GET',
            dataType: 'json',
            async: true,
            success: function(message){
                if (message.result === 'success'){
                    // alert(message.voteList);
                    console.log(message);
                    this.setState({
                        voteList: JSON.parse(message.voteList),
                        isFirst: false
                    });
                } else{
                    alert('获取voteList失败');
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
        // ajax 异步获取 voteList
        this.getVoteList();
    }
    
    componentWillUnmount(){
        // 组件卸载前，停止没有完成的ajax请求
        this.serverRequest.abort();
    }
    
    render(){
        var voteItems;
        if (this.state.voteList === undefined){
            if (this.state.isFirst){
                voteItems = '';
            } else{
                voteItems = '还没有人创建投票，快来创建吧！';
            }
        } else{
            voteItems = this.state.voteList.map((vote, index) => {
                return <VoteItemView vote={vote} key={vote.voteId} index={index} />
            });
        }
        return (
            <ul>{voteItems}</ul>
        );
    }
}


class VoteItemView extends React.Component{
    constructor(props){
        super(props);
    }
    
    componentDidMount(){        
        // 添加 a: hover
        $(this.refs.voteName).hover(
            (event) => {
                $(event.target).css('text-decoration', 'underline');
            },
            (event) => {
                $(event.target).css('text-decoration', 'none');
            }
        );
    }

    handleBtnClick(url){
        $(location).attr('href', url);
    }
    
    render(){
        const itemUrl = 'addPoll.html?voteId=' + this.props.vote.voteId + '&optionType=' + this.props.vote.voteType;
        
        return (
            <li style={this.props.liStyle} className='voteItem'>
                <div className='voteInfo' style={this.props.voteInfoStyle}>
                    <div style={{display: 'inline-block', marginBottom: 4}}>
                        <h3 style={this.props.h3Style}><a ref='voteName' href={itemUrl} style={this.props.aStyle}>{this.props.vote.voteName}</a></h3>
                    </div>
                    <VoteItemDescs voteType={this.props.vote.voteType} />
                </div>
                <div className='addPoll' style={this.props.addPollStyle}>
                    <div style={{textAlign: 'right', display: 'block'}}>
                        <button style={this.props.btnStyle} onClick={() => {this.handleBtnClick(itemUrl)} }>
                            <img src='images/v.png' style={this.props.imgStyle} />
                            Vote
                        </button>
                    </div>
                </div>
            </li>
        );
    }
}

VoteItemView.defaultProps = {
    liStyle: {
        display: 'flex',
        paddingTop: 24,
        paddingBottom: 24,
        width: '100%',
        borderBottom: '1px solid #e1e4e8'
    },
    voteInfoStyle: {
        display: 'inline-block',
        width: '75%'
    },
    h3Style: {
        wordBreak: 'break-all',
        fontWeight: 600,
        fontSize: 20
    },
    aStyle: {
        color: '#0366d6',
        textDecoration: 'none',
        backgroundColor: 'initial',
        fontFamily: '-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica,Arial,sans-serif,Apple Color Emoji,Segoe UI Emoji'
    },
    addPollStyle: {
        display: 'flex',
        justifyContent: 'space-around',
        flexDirection: 'column',
        width: '25%'
    },
    btnStyle: {
        padding: '3px 12px',
        fontFamily: '-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica,Arial,sans-serif,Apple Color Emoji,Segoe UI Emoji',
        fontSize: 12,
        lineHeight: '20px',
        color: '#24292e',
        backgroundColor: '#fafbfc',
        border: '1px solid rgba(27, 31, 35, .15)',
        boxShadow: '0 1px 0 rgba(27,31,35,.04), inset 0 1px 0 hsla(0,0%,100%,.25)',
        transition: 'background-color .2s cubic-bezier(.3,0,.5,1)',
        position: 'relative',
        display: 'inline-block',
        fontWeight: 500,
        whiteSpace: 'nowrap',
        verticalAlign: 'middle',
        cursor: 'pointer',
        borderRadius: 6
    },
    imgStyle: {
        verticalAlign: 'text-top',
        marginRight: 4,
        display: 'inline-block',
        width: 16,
        height: 16
    }
};


class VoteItemDescs extends React.Component{
    constructor(props){
        super(props);
    }
    
    render(){
        var type = '未定义';
        var color = '#3572A5';
        if (this.props.voteType === 0){
            type = '普通投票';
            color = '#f1e05a';
        } else if (this.props.voteType === 1){
            type = '图像投票';
            color = '#e34c26';
        } else if (this.props.voteType === 2){
            type = '音频投票';
            color = '#b07219';
        } else if (this.props.voteType === 3){
            type = '视频投票';
            color = '#00B4AB';
        }
        var colorSpanStyle = {
            position: 'relative',
            top: '1px',
            display: 'inline-block',
            width: 12,
            height: 12,
            borderRadius: '50%',
            backgroundColor: color
        };
        return (
            <div style={this.props.divStyle}>
                <span style={{marginRight: 16, marginLeft: 0}}>
                    <span style={colorSpanStyle}></span>
                    <span style={{marginLeft: 5}}>{type}</span>
                </span>
            </div>
        );
    }
}

VoteItemDescs.defaultProps = {
    divStyle: {
        fontSize: 12,
        marginTop: 8,
        color: '#586069',
        display: 'block'
    }
};




ReactDOM.render(
    <VoteView />,
    $('#voteView').get(0)
);
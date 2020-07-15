class OptionView extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {
            maxChoice: 3,
            isFirst: true,
            optionType: this.getQueryString('optionType'),
            voteId: this.getQueryString('voteId')
        };
        this.getOptionList = this.getOptionList.bind(this);
        this.handleVoteClick = this.handleVoteClick.bind(this);
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
                        optionList: message.optionList,
                        isFirst: false
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
    
    handleVoteClick(){
        if ($('input[type=checkbox]:checked').length < 1){
            alert('最少选择一个');
            return;
        }
        var checkList = [];
        const that = this;
        $.each($('input[type=checkbox]:checked'), function(index){
            checkList.push($(this).val());
            if (index === $('input[type=checkbox]:checked').length - 1){
                that.addPolls(checkList);
            }
        });
    }
    
    addPolls = (checkList) => {
        const data = {
            optionType: this.state.optionType,
            voteId: this.state.voteId,
            optionIdList: JSON.stringify(checkList)
        };
        // 发送post请求
        $.ajax({
            url: 'addPoll.do',
            type: 'POST',
            dataType: 'json',
            data: data,
            async: true,
            success: function(message){
                console.log(message);
                if (message.result === 'success'){
                    alert('投票成功，即将跳转到结果页面');
                    $(location).attr('href', 'result.html?optionType=' + this.state.optionType + '&voteId=' + this.state.voteId);
                } else{
                    alert('投票失败！' + message.result);
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
        // ajax 异步获取 optionList
        this.getOptionList();
    }
    
    componentWillUnmount(){
        // 组件卸载前，停止没有完成的ajax请求
        this.serverRequest.abort();
    }
    
    render(){
        var optionItems;
        if (this.state.optionList === undefined){
            if (this.state.isFirst){
                optionItems = '';
            } else{
                optionItems = '此投票还未添加选项';
            }
        } else{
            optionItems = this.state.optionList.map((option, index) => {
                return <OptionItemView option={option} key={option.optionId} 
                    index={index} optionType={this.state.optionType} 
                    maxChoice={this.state.maxChoice} />
            });
        }
        return (
            <div>
                <h2 style={{textAlign: 'center', marginTop: 25}}>投票页面</h2>
                <h5 style={{textAlign: 'center', marginTop: 15, marginBottom: 40}}>最少投1票，最多投{this.state.maxChoice}票</h5>
                <ul style={{marginBottom: 30}}>{optionItems}</ul>
                <div id='voteSubmit' style={{marginBottom: 50, textAlign: 'center'}}>
                    <button onClick={this.handleVoteClick} style={{backgroundColor: 'transparent', border: 'none', width: '80px', height: '80px', cursor: 'pointer', textAlign: 'center', outline: 'none'}}>
                        <img src='images/vote.png' alt='vote' title='vote' style={{width: '70px', height: '70px'}} />
                    </button>
                </div>
            </div>
        );
    }
}


class OptionItemView extends React.Component{
    constructor(props){
        super(props);
    }
    
    render(){
        const VoteType = {
                NORMBAL_OPTION: 0,
                IMG_OPTION: 1,
                AUDIO_OPTION: 2,
                VIDEO_OPTION: 3
            };
        
        var optionDescs = [];
        for (var i=0, j=1; j<6; j++){
            if (this.props.option['optionDesc'+j] != undefined && this.props.option['optionDesc'+j] !== ''){
                optionDescs[i] = <span key={i} style={{fontSize: 6, left: '-3em', color: 'orange', lineHeight: 0, top: '1.7em', marginLeft: 15}}>●</span>;
                i += 1;
                optionDescs[i] = <span key={i} style={{position: 'relative', width: 40, textAlign: 'justify', height: '1em', marginLeft: 15}}>{this.props.option['optionDesc' + j]}</span>;
                i += 1;
            }
        }
        
        if (this.props.optionType == VoteType.NORMBAL_OPTION){
            return <NormbalOptionItemView option={this.props.option} optionDescs={optionDescs} maxChoice={this.props.maxChoice} />;
        } else if (this.props.optionType == VoteType.IMG_OPTION){
            return <ImgOptionItemView option={this.props.option} optionDescs={optionDescs} maxChoice={this.props.maxChoice} />;
        } else if (this.props.optionType == VoteType.AUDIO_OPTION){
            return <AudioOptionItemView option={this.props.option} optionDescs={optionDescs} maxChoice={this.props.maxChoice} />;
        } else if (this.props.optionType == VoteType.VIDEO_OPTION){
            return <VideoOptionItemView option={this.props.option} optionDescs={optionDescs} maxChoice={this.props.maxChoice} />;
        } else{
            return null;
        }
    }
}


class NormbalOptionItemView extends React.Component{
    constructor(props){
        super(props);
    }
    
    render(){
        return (
            <li style={{display: 'flex', width: '100%', height: '30px', marginTop: 20}}>
                <StyledCheckBox optionId={this.props.option.optionId} maxChoice={this.props.maxChoice} />
                <div style={{display: 'inline-block', width: '60%', textAlign: 'left'}}>
                    <label htmlFor={this.props.option.optionId} style={{cursor: 'pointer'}}>
                        <span style={{color: 'orange', fontSize: 24, lineHeight: 0.8, display: 'none'}}>*</span>
                        {this.props.optionDescs}
                    </label>
                </div>
            </li>
        );
    }
}


class ImgOptionItemView extends React.Component{
    constructor(props){
        super(props);
    }
    
    render(){
        return (
            <li style={{display: 'flex', width: '100%', minHeight: '80px', marginTop: 20}}>
                <StyledCheckBox optionId = {this.props.option.optionId} maxChoice={this.props.maxChoice} />
                <div style={{display: 'inline-block', width: '60%', textAlign: 'left'}}>
                    <label htmlFor={this.props.option.optionId} style={{cursor: 'pointer'}}>
                        <span style={{color: 'orange', fontSize: 24, lineHeight: 0.8, display: 'none'}}>*</span>
                        {this.props.optionDescs}
                        <br />
                        <img htmlFor={this.props.option.optionId} src={this.props.option.imgUrl} style={{maxWidth: 200, marginTop: 15, marginLeft: 20}}/>
                    </label>
                </div>
            </li>
        );
    }
}


class AudioOptionItemView extends React.Component{
    constructor(props){
        super(props);
    }
    
    render(){
        return (
            <li style={{display: 'flex', width: '100%', minHeight: '80px', marginTop: 20}}>
                <StyledCheckBox optionId = {this.props.option.optionId} maxChoice={this.props.maxChoice} />
                <div style={{display: 'inline-block', width: '60%', textAlign: 'left'}}>
                    <label htmlFor={this.props.option.optionId} style={{cursor: 'pointer'}}>
                        <span style={{color: 'orange', fontSize: 24, lineHeight: 0.8, display: 'none'}}>*</span>
                        {this.props.optionDescs}
                        <br />
                        <audio controls style={{marginTop: 15, marginLeft: 20}}>
                            <source src={this.props.option.audioUrl}></source> 您的浏览器不支持 html5 audio元素！
                        </audio>
                    </label>
                </div>
            </li>
        );
    }
}


class VideoOptionItemView extends React.Component{
    constructor(props){
        super(props);
    }
    
    render(){
        return(
            <li style={{display: 'flex', width: '100%', minHeight: '80px', marginTop: 20}}>
                <StyledCheckBox optionId = {this.props.option.optionId} maxChoice={this.props.maxChoice} />
                <div style={{display: 'inline-block', width: '60%', textAlign: 'left'}}>
                    <label htmlFor={this.props.option.optionId} style={{cursor: 'pointer'}}>
                        <span style={{color: 'orange', fontSize: 24, lineHeight: 0.8, display: 'none'}}>*</span>
                        {this.props.optionDescs}
                        <br />
                        <video controls style={{maxWidth: 250, marginTop: 15, marginLeft: 20}}>
                            <source src={this.props.option.videoUrl}></source> 您的浏览器不支持 html5 video元素！
                        </video>
                    </label>
                </div>
            </li>        
        );
    }
}


class StyledCheckBox extends React.Component{
    constructor(props){
        super(props);
    }
    
    componentDidMount(){
        // 复选框选中、取消选中
        $(this.refs.checkBtn).change(() => {
            if ($('input[type=checkbox]:checked').length > this.props.maxChoice){
                alert ('最多选择' + this.props.maxChoice + '个');
                $(this.refs.checkBtn).prop('checked', false);
                return;
            }
            if ($(this.refs.checkBtn).is(':checked')){
                // 选中
                $(this.refs.checkLabel).find('label').eq(0).css({'background-color' : 'rgb(53, 183, 111)', 'border' : '5px #EEE solid'});
            } else{
                // 取消选中
                $(this.refs.checkLabel).find('label').eq(0).css({'background-color': 'orange', 'border': 'none'});
            }
        });
    }
    
    render(){
        const divStyle = {
            display: 'inline-block',
            width: '40%',
            textAlign: 'right'
        };
        
        const inputStyle = {
            position: 'absolute',
            clip: 'rect(0, 0, 0, 0)'
        };
        const labelStyle = {
            display: 'inline-block',
            width: 40,
            marginTop: 0,
            marginLeft: 5,
            textAlign: 'left',
            boxSizing: 'border-box',
            cursor: 'pointer'
        };
        const beforeLabelStyle = {
            display: 'inline-block',
            width: 20,
            height: 20,
            background: 'orange',
            verticalAlign: 'middle',
            marginTop: -3,
            marginRight: 5,
            borderRadius: '50%',
            boxSizing: 'border-box',
            transition: 'background ease-in .5s',
            cursor: 'pointer'
        };
        
        /* const Wrapper = window.styled.div`
            label:hover {
                cursor: pointer;
            }
        `; */
        
        return (
            <div style={divStyle}>
                <input type='checkbox' name='optionItem' value={this.props.optionId}
                    id={this.props.optionId} style={inputStyle} ref='checkBtn' />
                <label htmlFor={this.props.optionId} style={labelStyle} ref='checkLabel'>
                    <label className='before' htmlFor={this.props.optionId} style={beforeLabelStyle}></label>
                </label>
            </div>
        );
    }
}


ReactDOM.render(
    <OptionView />,
    $('#optionView').get(0)
);
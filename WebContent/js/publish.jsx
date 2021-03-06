class ErrorMessage extends React.Component{
    constructor(props){
        super(props);
        
        this.closeClick = this.closeClick.bind(this);
    }
    
    closeClick(){
        $('#error_message button:first').click(() => {
            $('#error_message').css('display', 'none');
        });
    }
    
    render(){
        return (
            <div>
                <div className="white_content" style={this.props.whiteContent}></div>
                <div id="error_message" style={this.props.errorMessage}>
                    <label style={this.props.label}>验证错误，请正确填写信息</label>
                    <button aria-label="Close" style={this.props.button} onClick={this.closeClick}>X</button>
                </div>
            </div>
        );
    }
}

ErrorMessage.defaultProps = {
    whiteContent: {border: 'none', height: 1, marginTop: '9%'},
    errorMessage: {
        border: 'solid 1px rgb(203, 175, 179)',
        backgroundColor: 'rgb(255, 218, 223)',
        width: 255,
        height: 30,
        marginLeft: '38.5%',
        borderRadius: 3,
        paddingLeft: 40,
        paddingTop: 5,
        position: 'relative',
        display: 'none'
    },
    label: {color: 'rgb(149, 9, 19)'},
    button: {
        border: 'none',
        backgroundColor: 'rgb(255, 218, 223)',
        marginLeft: 25,
        color: 'rgb(149, 9, 19)',
        cursor: 'pointer',
        fontSize: 16
    }
};


class VotePublish extends React.Component{
    constructor(props){
        super(props);
        
        this.submitClick = this.submitClick.bind(this);
        this.cancelClick = this.cancelClick.bind(this);
        this.checkVote = this.checkVote.bind(this);
    }
   
    cancelClick(){
        alert('cancel');
    }
    
    submitClick(){
        if (!this.checkVote()){
            this.props.inputError();
            return;
        }
        // 发送提交请求
        var reqData = {
            voteName: $('#voteName').val().trim(),
            voteType: $('#voteType').val().trim(),
            voteEndTime: $('#voteEndTime').val().trim(),
            voteDesc1: $('#voteDesc1').val().trim()
        };
        var index = 2;
        if ($('#voteDesc2').val().trim() !== ''){
            reqData['voteDesc' + index] = $('#voteDesc2').val().trim();
            index = index + 1;
        }
        if ($('#voteDesc3').val().trim() !== ''){
            reqData['voteDesc' + index] = $('#voteDesc3').val().trim();
            index = index + 1;
        }
        if ($('#voteDesc4').val().trim() !== ''){
            reqData['voteDesc' + index] = $('#voteDesc4').val().trim();
            index = index + 1;
        }
        if ($('#voteDesc5').val().trim() !== ''){
            reqData['voteDesc' + index] = $('#voteDesc5').val().trim();
            index = index + 1;
        }
        $.ajax({
            url: 'publishVote.do',
            type: 'POST',
            dataType: 'json',
            data: reqData,
            async: true,
            success: function(data){
                var result = data.result;
                if (result === 'success'){
                    alert('发布成功，即将跳转');
                    $(location).attr('href', 'addOption.html?voteName=' + reqData.voteName);
                } else{
                    alert('发布失败');
                    console.error(result);
                }
            },
            error: function(err){
                alert('发布失败');
                console.error(err.responseText);
            }
        });
    }
    
    checkVote(){
        if ($('#voteName').val().trim() === ''){
            return false;
        }
        if ($('#voteType').val().trim() === ''){
            return false;
        }
        if ($('#voteEndTime').val().trim() === ''){
            return false;
        }
        if ($('#voteDesc1').val().trim() === ''){
            return false;
        }
        return true;
    }
    
    render(){
        return (
            <div style={this.props.voteDiv}>
                <form name="voteForm">
                    <label htmlFor="voteName">投票名<span style={this.props.span}> *</span></label><br />
                    <input style={this.props.inputStyle} type="text" id="voteName" name="voteName" tabindex="1" /><br />
                    <label htmlFor="voteType">投票类型<span style={this.props.span}> *</span></label><br />
                    <select style={this.props.selectStyle} id="voteType" name="voteType" tabindex="2">
                        <option value="0">普通投票</option>
                        <option value="1">图像投票</option>
                        <option value="2">音频投票</option>
                        <option value="3">视频投票</option>
                    </select><br />
                    <label htmlFor="voteEndTime">投票结束时间<span style={this.props.span}> *</span></label><br />
                    <input style={this.props.inputStyle} type="datetime" id="voteEndTime" name="voteEndTime" tabindex="3" /><br />
                    <label htmlFor="voteDesc1">投票描述1<span style={this.props.span}> *</span></label><br />
                    <input style={this.props.inputStyle} type="text" id="voteDesc1" name="voteDesc1" tabindex="4" /><br />
                    <label htmlFor="voteDesc2">投票描述2</label><br />
                    <input style={this.props.inputStyle} type="text" id="voteDesc2" name="voteDesc2" tabindex="5" /><br />
                    <label htmlFor="voteDesc3">投票描述3</label><br />
                    <input style={this.props.inputStyle} type="text" id="voteDesc3" name="voteDesc3" tabindex="6" /><br />
                    <label htmlFor="voteDesc4">投票描述4</label><br />
                    <input style={this.props.inputStyle} type="text" id="voteDesc4" name="voteDesc4" tabindex="7" /><br />
                    <label htmlFor="voteDesc5">投票描述5</label><br />
                    <input style={this.props.inputStyle} type="text" id="voteDesc5" name="voteDesc5" tabindex="8" /><br />
                    <div id="voteBtn">
                        <input style={this.props.cancelStyle} type="button" name="cancel" id="cancel" value="取消" tabindex="9" 
                            onClick={this.cancelClick} />
                        <input style={this.props.submitStyle} type="button" name="publish" id="publish" value="提交" tabindex="10" 
                            onClick={this.submitClick} />
                    </div>
                </form>
            </div>
        );
    }
}

VotePublish.defaultProps = {
    voteDiv: {
        backgroundColor: 'rgba(255, 255, 255, 0.1)',
        width: 255,
        height: 660,
        marginLeft: '38.5%',
        marginTop: 10,
        marginBottom: 50,
        paddingTop: 20,
        paddingLeft: 40,
        border: 'solid 1px rgb(206, 213, 218)',
        borderRadius: 5
    },
    inputStyle: {
        marginTop: 10,
        marginBottom: 15,
        width: 200,
        height: 26,
        paddingLeft: 10,
        fontSize: 14,
        border: 'solid 1px rgb(206, 213, 218)',
        borderRadius: 3
    },
    selectStyle: {
        marginTop: 10,
        marginBottom: 15,
        width: 214,
        height: 30,
        paddingLeft: 10,
        fontSize: 14,
        border: 'solid 1px rgb(206, 213, 218)',
        borderRadius: 3
    },
    submitStyle: {
        marginLeft: 57,
        marginTop: 5,
        backgroundColor: '#94d3a2',
        borderColor: 'rgba(27, 31, 35, .2)',
        width: 62,
        height: 24
    },
    cancelStyle: {
        marginLeft: 17,
        marginTop: 5,
        backgroundColor: '#94d3a2',
        borderColor: 'rgba(27, 31, 35, .2)',
        width: 62,
        height: 24
    },
    span: {
        color: 'red'
    }
};


class Vote extends React.Component{
    constructor(props){
        super(props);
        
        this.inputError = this.inputError.bind(this);
    }
    
    inputError(){
        $('#error_message').css('display', 'block');
        $(window).scrollTop('#error_message');
    }
    
    render(){
        return(
            <div>
                <ErrorMessage />
                <VotePublish inputError={this.inputError}/>
            </div>
        );
    }
}


ReactDOM.render(
    <Vote />,
    $('#votePublish').get(0)
);
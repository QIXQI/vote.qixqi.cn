// Priorities
const priorities = {
    VISITOR: -2,
    THIRD_PARTY_USER: -1,
    USER: 0,
    ADMINISTRATOR: 1
};

// Status
const Status = {
    OFFLINE: 0,
    ONLINE: 1
};

// Header
class Header extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {
            priority: priorities.USER,
            userDroped: false,
            searchFlag: false
        };
        this.updateUserDroped = this.updateUserDroped.bind(this);
        this.updateSearchFlag = this.updateSearchFlag.bind(this);
    }
    
    updateUserDroped(){
        this.setState(state => ({
            userDroped: !state.userDroped
        }));
    }
    
    updateSearchFlag(){
        this.setState(state => ({
            searchFlag: !state.searchFlag
        }));
    }
    
    componentDidMount(){
        // 头像悬停框消失
        $(document).click((event) => {
            if ($(event.target).attr('class') === 'avatarDiv'){
                return;
            }
            if ($(event.target).attr('class') === 'searchDiv'){
                return;
            }
            if (this.state.userDroped){
                this.setState(state => ({
                    userDroped: !state.userDroped
                }));
            }
            if (this.state.searchFlag){
                this.setState(state => ({
                    searchFlag: !state.searchFlag
                }));
            }
        });
    }
    
	render(){
	    const visitorInfo = {};
	    
	    const qqUserInfo = {
            avatar: 'images/g.jpg',
            openid: '1000112'
	    };
	    
	    const userInfo = {
	        userId: 201792179,
	        userName: '郑翔',
	        userSex: '男',
	        userPriority: priorities.USER,
	        userJoinTime: '2020-07-05 12:00:00',
	        userLoginTime: '2020-07-05 12:00:00',
	        userEmail: 'zhengxiang4056@gmail.com',
	        userPhone: '19818965587',
	        userStatus: Status.ONLINE,
	        userAvatar: 'images/avatar/default.jpg',
	        userBirthday: '1999-04-22'
	    };
	    
		return (
			<div style = {this.props.style}>
		        <Icon site={this.props.icon.site} imgUrl={this.props.icon.imgUrl} imgAlt={this.props.icon.imgAlt} imgTitle={this.props.icon.imgTitle}/>
		        <Menu priority={this.state.priority} menuItems={this.props.menu[this.state.priority + 2]}/>
		        <Search updateSearchFlag = {this.updateSearchFlag} flag={this.state.searchFlag} />
		        <UserInfo priority={this.state.priority} userInfo = {userInfo} isDroped={this.state.userDroped}
		            updateUserDroped = {this.updateUserDroped}/>
			</div>
		);
	}
}

Header.defaultProps = {
    style: {
        height: 53,
        paddingLeft: 16,
        backgroundColor: 'rgb(35, 41, 47)'
    },
    icon: {
        site: 'https://qixqi.cn',
        imgUrl: 'images/icon/vote_white.png',
        imgAlt: 'qixqi.cn',
        imgTitle: 'qixqi.cn'
    },
    menu: [
        [   // visitor
            {
                name: '投票',
                url: 'index.html'
            }
        ],
        [   // third_party_user
            {
                name: '投票',
                url: 'index.html'
            }
        ],
        [   // user
            {
                name: '投票',
                url: 'index.html'
            },
            {
                name: '发布',
                url: 'publishVote.html'
            }
        ]
    ]
}


// Icon
class Icon extends React.Component{
    constructor(props){
        super(props);
    }
    
    render(){
        return (
            <div style = {this.props.style}>
                <a href={this.props.site}>
                    <img style={this.props.imgStyle} src={this.props.imgUrl} alt={this.props.imgAlt} title={this.props.imgTitle} />
                </a>
            </div>
        );
    }
}


Icon.defaultProps = {
    style: {
        position: 'absolute',
        marginTop: 10
    },
    imgStyle: {
        width: 32,
        height: 32,
        cursor: 'pointer'
    }
}


// Menu
class Menu extends React.Component{
    constructor(props){
        super(props);
    }
    
    render(){
        const listItems = this.props.menuItems.map((item, index) => 
            <a href={item.url} key={index} style={this.props.aStyle}><b>{item.name}</b></a>
        );
        return (
            <div style={this.props.style}>
                {listItems}
            </div>
        );
    }
}

Menu.defaultProps = {
    style: {
        position: 'absolute',
        marginTop: 15,
        marginLeft: 60
    },
    aStyle: {
        color: 'white',
        textDecoration: 'none',
        marginRight: 64
    }
}


// Search
class Search extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            flag: false
        };
        
        this.searchClick = this.searchClick.bind(this);
        this.submitClick = this.submitClick.bind(this);
    }
    
    componentWillReceiveProps(newProps){
        if(this.state.flag !== newProps.flag){
            this.setState(state => ({
                flag: !state.flag
            }));
        }
    }
    
    searchClick(event){
        event.stopPropagation();
        this.props.updateSearchFlag();
    }
    
    submitClick(event){
        event.stopPropagation();
        this.props.updateSearchFlag();
        alert('查询');
    }
    
    render(){
        const searchStyle = {
            width: 295,
            height: 25,
            paddingLeft: 10,
            borderRadius: 5,
            outline: 'none',
            border: 'none', 
            backgroundColor: !this.state.flag ? 'rgb(62, 67, 72)' : 'rgb(250, 251, 252)',
            position: 'absolute',
            fontSize: 14
        };
        
        return (
            <div style={this.props.style} className='searchDiv'>
                <input className='searchDiv' type="text" name="search" style={searchStyle} placeholder="本站搜索" onClick={this.searchClick}/> 
                <input className='searchDiv' type="button" name="submit" style={this.props.submit} onClick={this.submitClick} />
            </div>
        );
    }
}

Search.defaultProps = {
    style: {
        position: 'absolute',
        marginLeft: '64%',
        marginTop: 15,
        width: 300,
        height: 30
    },
    submit: {
        height: 30,
        width: 25,
        position: 'absolute',
        background: 'url("images/search.png") no-repeat -1px 1px',
        marginLeft: 280,
        border: 'none',
        outline: 'none',
        cursor: 'pointer'
    }
}


// UserInfo
class UserInfo extends React.Component{
    constructor(props){
        super(props);
       
        this.state = {isDroped: false};
        this.avatarClick = this.avatarClick.bind(this);
    }
    
    avatarClick(event){
        event.stopPropagation();
        this.props.updateUserDroped();
    }
    
    componentWillReceiveProps(newProps){
        if(this.state.isDroped !== newProps.isDroped){
            this.setState(state => ({
                isDroped: !state.isDroped
            }));
        }
    }
    
    render(){
        if (this.props.priority === priorities.VISITOR){
            return (
                <div style={this.props.visitor}>
                    <a href='register.html' style = {this.props.a}><b>注册</b></a>
                    <a href='login.html' style = {this.props.a}><b>登录</b></a>
                </div>
            );
            
        } else if(this.props.priority === priorities.THIRD_PARTY_USER){
            return (
                <div style={this.props.user}>
                    <a href='register.html' style={this.props.a}><b>注册</b></a>
                    <div style={{display: 'inline-block', marginRight: 32, verticalAlign: 'middle', cursor: 'pointer'}}>
                        <img src={this.props.userInfo.avatar} alt={this.props.userInfo.openid} 
                            title={this.props.userInfo.openid} style={{width: 20, height: 20, borderRadius: '50%'}}/>
                        <span style={this.props.span}></span>
                    </div>
                </div>
            );
        } else if(this.props.priority >= priorities.USER){
            return (
                <div style={this.props.user}>
                    <div style={{display: 'inline-block', marginRight: 32, verticalAlign: 'middle', cursor: 'pointer'}}>
                        <img src='images/publish.png' alt='发布' title='发布' style={{width: 20, height: 20}} />
                        <span style={this.props.span}></span>
                    </div>
                    <div style={{display: 'inline-block', marginRight: 32, verticalAlign: 'middle'}}>
                        <div style={{cursor: 'pointer'}} onClick={this.avatarClick} className='avatarDiv'>
                            <img className='avatarDiv' src={this.props.userInfo.userAvatar} alt={this.props.userInfo.userName} 
                                title={this.props.userInfo.userName} style={{width: 20, height: 20, borderRadius: '50%'}} />
                            <span className='avatarDiv' style={this.props.span}></span>
                        </div>
                        <UserDrop priority={this.props.priority} userInfo = {this.props.userInfo} isDroped={this.state.isDroped} />
                    </div>
                </div>
            );
        }
        return null;
    }
}

UserInfo.defaultProps = {
    visitor: {
        marginTop: 15,
        float: 'right',
        marginRight: 10
    },
    a: {
        color: 'white',
        textDecoration: 'none',
        marginRight: 32
    },
    user: {
        marginTop: 15,
        float: 'right',
        marginRight: 0
    },
    span: {
        display: 'inline-block',
        width: 0,
        height: 0,
        color: 'white',
        verticalAlign: 'middle',
        content: '',
        borderTopStyle: 'solid',
        borderTopWidth: 4,
        borderRight: '4px solid transparent',
        borderBottom: '0 solid transparent',
        borderLeft: '4px solid transparent',
        marginLeft: 3
    }
}


// UserDrop
class UserDrop extends React.Component{
    constructor(props){
        super(props);
    }
    
    componentDidMount(){
        /* 头像悬停框 <p>的hover事件 */
        $('.pHover').hover(
            (event) => {
                /* 进入p */
                // console.log($(event.target).text());
                $(event.target).css({'background-color': 'rgb(0, 89, 222)', 'color': 'white'});
            }, 
            () => {
                /* 离开p */
                $(event.target).css({'background-color': 'white', 'color': '#24292e'});
            });
        
        /* 阻止userDrop的冒泡事件 */
        $('#userDrop').click(() => {
            event.stopPropagation();
        });
    }
    
    componentWillReceiveProps(newProps){
        /* 控制头像悬停框是否可见 */
        if (newProps.isDroped){
            // 可见
            $('#userDrop').css('display', 'block');
        } else{
            // 不可见
            $('#userDrop').css('display', 'none');
        }
    }
    
    render(){
        if (this.props.priority === priorities.VISITOR){
            return null;
        } else if(this.props.priority === priorities.THIRD_PARTY_USER){
            
        } else if(this.props.priority >= priorities.USER){
            return (
                <div style={this.props.userDrop} id='userDrop'>
                    <em style={this.props.em}></em><span style={this.props.span}></span>
                    <div style={this.props.menuHead}>
                        <p style={this.props.p}>{this.props.userInfo.userName}</p>
                        <p style={this.props.p}>{this.props.userInfo.userStatus === Status.ONLINE ? '在线' : '离线'}</p>
                    </div>
                    <div style={this.props.menuBody}>
                        <a href='' style={this.props.a}><p style={this.props.p} className='pHover'>个人信息</p></a>
                        <a href='' style={this.props.a}><p style={this.props.p} className='pHover'>我的发布</p></a>
                        <a href='' style={this.props.a}><p style={this.props.p} className='pHover'>我的投票</p></a>
                    </div>
                    <div style={this.props.menuTail}>
                        <a href='' style={this.props.a}><p style={this.props.p} className='pHover'>登录日志</p></a>
                        <p style={this.props.p} className='pHover'>注销</p>
                    </div>
                </div>    
            );
        }
        return null;
    }
}

UserDrop.defaultProps = {
    a: {
        color: '#24292e',
        textDecoration: 'none'
    },
    p: {
        padding: '4px 16px'
    },
    userDrop: {
        display: 'none',
        position: 'absolute',
        backgroundColor: 'white',
        minWidth: 158,
        boxShadow: '0px 8px 16px 0px rgba(0, 0, 0, 0.2)',
        marginLeft: -125,
        marginTop: 5,
        borderRadius: 3,
        border: '1px solid rgb(193, 196, 198)',
        textDecoration: 'none'
    },
    em: {
        display: 'block',
        borderWidth: 7.5,
        position: 'absolute',
        top: -15,
        left: 131,
        borderStyle: 'solid dashed dashed',
        borderColor: 'transparent transparent white transparent',
        fontSize: 0,
        lineHeight: 0
    },
    span: {
        display: 'block',
        borderWidth: 7.5,
        position: 'absolute',
        top: -14,
        left: 131,
        borderStyle: 'solid dashed dashed',
        borderColor: 'transparent transparent white transparent',
        fontSize: 0,
        lineHeight: 0
    },
    menuHead: {
        fontSize: 16,
        borderBottom: '1px solid rgb(225, 228, 233)',
        boxSizing: 'border-box',
        paddingTop: 4,
        paddingBottom: 4
    },
    menuBody: {
        color: '#24292e',
        borderBottom: '1px solid rgb(225, 228, 233)',
        paddingTop: 10,
        paddingBottom: 10
    },
    menuTail: {
        color: '#24292e',
        paddingTop: 9,
        paddingBottom: 9
    }
    
    
}




ReactDOM.render(
	<Header />,
	$('#header').get(0)
);
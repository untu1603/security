import {Link} from "react-router-dom";
const item = {
    textDecoration: 'none',
    padding: "10px",
    color: "green"
};
function Navbar(){
    return <>
        <nav className="navbar navbar-expand-sm bg-light">
            <div className="container-fluid">
                <Link to="/" style={item}>
                <a className="navbar-brand" >
                    <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/d/dc/CapShield05.jpg/220px-CapShield05.jpg" width="30" height="30" className="d-inline-block align-top"/>
                    Security
                </a>
                </Link>
                <div className="nav justify-content-end">
                            <Link to="/login" style={item}>
                                Đăng nhập
                            </Link>

                            <Link to="/register" style={item}>
                                Đăng ký
                            </Link>
                </div>
            </div>
        </nav>
    </>

}

export default Navbar

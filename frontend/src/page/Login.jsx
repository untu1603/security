import "../main.css"
import "../main.scss"

import {Link,useNavigate} from "react-router-dom";
import {useState} from "react";
import {useGoogleLogin} from "@react-oauth/google";
import call from "./Register.jsx"

const inputs = {
    username: "",
    password: "",
};
export default function Login(){
    const navigate = useNavigate()
    const loginGoogle = useGoogleLogin({
        onSuccess: codeResponse => console.log(codeResponse),
        flow: 'auth-code',
    });
    const [values, setValues] = useState(inputs);
    const handleSubmit = (event) => {
        event.preventDefault();
        console.log(values);
        call.post("/login",values)
            .then( response => {
                console.log(response)
                navigate("/home")
            }
        ).catch(
            e => {
                console.log(e)
            }
        )
    }
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setValues({
            ...values,
            [name]: value,
        });
    };
    return <>
        <div className="container-login100" style={{backgroundImage: "url(https://colorlib.com/etc/lf/Login_v4/images/bg-01.jpg)"}}>
            <div className="wrap-500 p-l-55 p-r-55 p-t-65 p-b-54">
                <form>
					<span className="login100-form-title p-b-49">
						Login
					</span>
                    <div className="wrap-input100 validate-input m-b-23" data-validate = "Username is reauired">
                        <span className="label-input100">Username</span>
                        <input className="input100" type="text" value={values.username}
                               onChange={handleInputChange}
                               name="username" placeholder="Type your username"></input>
                            <span className="focus-input100 " data-symbol="&#xf206;"></span>
                    </div>
                    <div className="wrap-input100 validate-input" data-validate="Password is required">
                        <span className="label-input100">Password</span>
                        <input className="input100" type="password" value={values.password}
                               onChange={handleInputChange}
                               name="password" placeholder="Type your password"></input>
                            <span className="focus-input100" data-symbol="&#xf190;"></span>
                    </div>
                    <div className="text-right p-t-8 p-b-31">
                        <a>
                            Forgot password?
                        </a>
                    </div>
                    <div className="container-login100-form-btn">
                        <div className="wrap-login100-form-btn">
                            <div className="login100-form-bgbtn"></div>
                            <button className="login100-form-btn" onClick={handleSubmit}>
                                Login
                            </button>
                        </div>
                    </div>
                    <div className="txt1 text-center p-t-54 p-b-20">
						<span>
							Or Sign Up Using
						</span>
                    </div>
                    <div className="flex-c-m">
                        <a  className="login100-social-item bg1">
                            <i className="fa fa-facebook"></i>
                        </a>

                        <a className="login100-social-item bg2">
                            <i className="fa fa-twitter"></i>
                        </a>

                        <a  className="login100-social-item bg3" onClick={() => loginGoogle()}>
                            <i className="fa fa-google" ></i>
                        </a>
                    </div>
                    <div className="flex-col-c p-t-155">
						<span className="txt1 p-b-17">
							Or Sign Up Using
						</span>
                        <Link to={{ pathname: '/register'}}  className="txt2">
                            Sign Up
                        </Link>
                    </div>
                </form>
            </div>
        </div>
    </>
}

import {Link} from "react-router-dom";
import "../main.css"
import "../main.scss"

import {useState} from "react";
import Repository from "../api/Repository.js";
import axios from "axios";
import data from "bootstrap/js/src/dom/data.js";
const inputs = {
    name: "",
    username: "",
    password: "",
};
export default function Register(){
    const [values, setValues] = useState(inputs);
    const handleSubmit = (event) => {
        event.preventDefault();
        Repository.post('auth/register',values).then(response =>{
            console.log(response.data)
        })
        console.log(values);
    }
    const handleS = (event) => {
        event.preventDefault();
        axios.get("https://accounts.google.com/o/oauth2/v2/auth",)
        console.log(values);
    }
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setValues({
            ...values,
            [name]: value,
        });
    };
    return <>
        <div className="container-login100">
            <div className="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
                <form className="login100-form validate-form">
					<span className="login100-form-title p-b-49">
						Sign Up
					</span>
                    <div className="wrap-input100 validate-input m-b-23" data-validate="Username is reauired">
                        <span className="label-input100">Name</span>
                        <input className="inputRegis" type="text" name="name"
                               value={values.name}
                               onChange={handleInputChange}
                               placeholder="Type your name"></input>
                        <span className="focus-inputRegis"></span>
                    </div>
                    <div className="wrap-input100 validate-input m-b-23" data-validate="Username is reauired">
                        <span className="label-input100">Username</span>
                        <input className="inputRegis" type="text" name="username"
                               value={values.username}
                               onChange={handleInputChange}
                               placeholder="Type your username"></input>
                        <span className="focus-inputRegis"></span>
                    </div>
                    <div className="wrap-input100 validate-input m-b-23" data-validate="Password is required">
                        <span className="label-input100">Password</span>
                        <input className="inputRegis" type="password" name="password"
                               value={values.password}
                               onChange={handleInputChange}
                               placeholder="Type your password"></input>
                        <span className="focus-inputRegis"></span>
                    </div>
                    <div className="wrap-input100 validate-input m-b-23" data-validate="Password is required">
                        <span className="label-input100">Repeat Password</span>
                        <input className="inputRegis" type="password" name="pass"
                               placeholder="Type your password"></input>
                        <span className="focus-inputRegis"></span>
                    </div>
                    <div className="text-right p-t-8 p-b-31">
                    </div>
                    <div className="container-login100-form-btn">
                        <div className="wrap-login100-form-btn">
                            <div className="login100-form-bgbtn"></div>
                            <button className="login100-form-btn" onClick={handleSubmit}>
                                Sign Up
                            </button>
                        </div>
                    </div>
                    <div className="txt1 text-center p-t-54 p-b-20">
						<span>
							Or Sign Up Using
						</span>
                    </div>
                    <div className="flex-c-m">
                        <a className="login100-social-item bg1">
                            <i className="fa fa-facebook"></i>
                        </a>

                        <a className="login100-social-item bg2">
                            <i className="fa fa-twitter"></i>
                        </a>

                        <a className="login100-social-item bg3">
                            <i className="fa fa-google"></i>
                        </a>
                    </div>
                    <div className="flex-col-c p-t-155">
						<span className="txt1 p-b-17">
							Or Sign Up Using
						</span>
                        <Link to={{pathname: '/register'}} className="txt2">
                            Sign Up
                        </Link>
                    </div>
                </form>
            </div>
        </div>
    </>
}
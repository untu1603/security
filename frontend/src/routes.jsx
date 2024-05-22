import React from "react"
const Login = React.lazy(() => import("./page/Login.jsx"))
const Register = React.lazy(() => import("./page/Register.jsx"))
const Home = React.lazy(() => import("./page/Home.jsx"))


const routes = [
    { path: "/register", element: <Register /> },
    { path: "/login", element: <Login /> },
    { path: "" , element: <Home/>}
]
export default routes

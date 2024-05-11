import React from "react"
const Login = React.lazy(() => import("./Login.jsx"))
const Register = React.lazy(() => import("./Register.jsx"))


const routes = [
    { path: "/register", element: <Register /> },
    { path: "/login", element: <Login /> },
]
export default routes

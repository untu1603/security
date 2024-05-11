import { Link } from "react-router-dom"

export default function Page404() {
    return (
        <div className="container d-flex flex-column justify-content-center align-items-center vh-100">
            <h1>404 - Page Not Found</h1>
            <p>The page you are looking for does not exist</p>
            <Link to="/" className={"back-to-home"}>Back to Home</Link>
        </div>
    )
}

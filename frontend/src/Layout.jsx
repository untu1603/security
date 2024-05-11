import Navbar from "./component/Navbar.jsx";
import {Outlet} from "react-router-dom";
import {Suspense} from "react";
import Loader from "./Loader.jsx";

export default function Layout(){
    return (<>
    <Navbar/>
        <Suspense fallback={Loader}>
            <Outlet/>
        </Suspense>
    </>)
}


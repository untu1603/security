import routes from "./routes";
import {RouterProvider,  createBrowserRouter} from "react-router-dom";
import Page404 from "./Page404.jsx";
import Layout from "././Layout.jsx";
function App() {
    const router = createBrowserRouter([
        {
            element: <Layout />,
            errorElement: <Page404 />,
            children: routes
        }
    ]);

    return <RouterProvider router={router} />;
}

export default App;

import routes from "./routes";
import {RouterProvider,  createBrowserRouter} from "react-router-dom";
import Page404 from "./page/Page404.jsx";
import Layout from "./page/Layout.jsx";

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

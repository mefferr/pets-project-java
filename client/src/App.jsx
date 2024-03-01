import {createBrowserRouter, RouterProvider,} from "react-router-dom";
import "./index.css";
import Main from "./views/Main.jsx";
import Login from "./views/Login.jsx";


export default function App() {
    const routes = [{path: "/", element: <Main/>}, {path: "/login", element: <Login />}];
    const router = createBrowserRouter(routes);

    return <RouterProvider router={router}></RouterProvider>
}
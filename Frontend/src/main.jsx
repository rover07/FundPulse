import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.jsx";
import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
  RouterProvider,
} from "react-router-dom";
import Layout from "./Layout.jsx";
import SignUp from "./Components/SignUp/SignUp.jsx";
import Login from "./Components/SignUp/Login.jsx";

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<Layout />}>
      <Route path="" element={<App />} />
      <Route path="signup" element={<SignUp />} />
      <Route path="login" element={<Login />} />
    </Route>
  )
);

{
  /* <Route path="" element={<Home />} />
      <Route path="about" element={<About />} />
      <Route path="contact" element={<Contact />} />
      <Route path="user/:userid" element={<User />} />
      <Route path="github" element={<Github />} loader={githubInfo} /> */
}

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);

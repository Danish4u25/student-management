import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Students from "./pages/Students";
import Courses from "./pages/Courses";

function App() {

  return (

      <BrowserRouter>

          <Routes>

              <Route
                  path="/"
                  element={<Login/>}
              />

              <Route
                  path="/dashboard"
                  element={<Dashboard/>}
              />

              <Route
                  path="/students"
                  element={<Students/>}
              />
              <Route
                  path="/courses"
                  element={<Courses/>}
              />
          </Routes>
      </BrowserRouter>

  );
}

export default App;
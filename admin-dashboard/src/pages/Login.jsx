import { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../services/api";

function Login() {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const login = async () => {

        try {

            const response = await API.post(
                "/auth/login",
                {
                    email,
                    password
                }
            );

            // Save login data
            localStorage.setItem(
                "token",
                response.data.token
            );

            localStorage.setItem(
                "role",
                response.data.role
            );

            localStorage.setItem(
                "username",
                response.data.username
            );

            alert("Login successful");

            console.log(
                "Login Response:",
                response.data
            );

            // Move to dashboard
            navigate("/dashboard");

        } catch (error) {

            console.log(
                "Full Error:",
                error
            );

            console.log(
                "Response:",
                error.response?.data
            );

            alert(
                error.response?.data?.message ||
                "Login failed"
            );
        }
    };

    return (

        <div
            style={{
                display: "flex",
                flexDirection: "column",
                width: "300px",
                margin: "100px auto",
                padding: "20px",
                border: "1px solid #ddd",
                borderRadius: "10px"
            }}
        >

            <h2>Admin Login</h2>

            <input
                type="email"
                placeholder="Email"
                value={email}
                onChange={(e) =>
                    setEmail(
                        e.target.value
                    )
                }
            />

            <br />

            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) =>
                    setPassword(
                        e.target.value
                    )
                }
            />

            <br />

            <button
                onClick={login}
            >
                Login
            </button>

        </div>
    );
}

export default Login;
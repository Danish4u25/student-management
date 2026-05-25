import { Link } from "react-router-dom";

function Sidebar(){

    return(

        <div
            style={{
                width:"220px",
                height:"100vh",
                background:"#222",
                color:"white",
                padding:"20px"
            }}
        >

            <h2>
                Admin Panel
            </h2>

            <hr/>

            <p>
                <Link
                    to="/students"
                    style={{
                        color:"white",
                        textDecoration:"none"
                    }}
                >
                    📚 Students
                </Link>
            </p>

            <p>
                <Link
                    to="/courses"
                    style={{
                        color:"white",
                        textDecoration:"none"
                    }}
                >
                    📖 Courses
                </Link>
            </p>

            <p>📅 Attendance</p>

            <p>📝 Grades</p>

            <p>👤 Users</p>

        </div>

    );

}

export default Sidebar;
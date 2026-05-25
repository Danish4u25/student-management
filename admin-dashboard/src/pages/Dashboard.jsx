import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";

function Dashboard(){

    return(

        <div
            style={{
                display:"flex"
            }}
        >

            <Sidebar/>

            <div
                style={{
                    flex:1
                }}
            >

                <Navbar/>

                <div
                    style={{
                        padding:"20px"
                    }}
                >

                    <h1>
                        Dashboard
                    </h1>

                    <div
                        style={{
                            display:"flex",
                            gap:"20px"
                        }}
                    >

                        <div
                            style={{
                                padding:"20px",
                                border:"1px solid gray"
                            }}
                        >
                            Students
                        </div>

                        <div
                            style={{
                                padding:"20px",
                                border:"1px solid gray"
                            }}
                        >
                            Courses
                        </div>

                        <div
                            style={{
                                padding:"20px",
                                border:"1px solid gray"
                            }}
                        >
                            Attendance
                        </div>

                        <div
                            style={{
                                padding:"20px",
                                border:"1px solid gray"
                            }}
                        >
                            Grades
                        </div>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default Dashboard;
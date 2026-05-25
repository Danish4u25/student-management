import { useEffect, useState } from "react";
import API from "../services/api";

function Courses() {

    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchCourses = async () => {

        try {

            const response =
                await API.get("/courses");

            console.log(
                "Courses Data:",
                response.data
            );

            setCourses(
                Array.isArray(response.data)
                    ? response.data
                    : []
            );

        } catch(error){

            console.log(
                "Error:",
                error
            );

            alert(
                "Failed to load courses"
            );

        } finally {

            setLoading(false);
        }
    };

    useEffect(() => {

        fetchCourses();

    }, []);

    if(loading){

        return(
            <div>
                Loading courses...
            </div>
        );
    }

    return (

        <div style={{padding:"20px"}}>

            <h2>
                Course List
            </h2>

            {courses.length === 0 ? (

                <p>
                    No courses found
                </p>

            ) : (

                <table
                    border="1"
                    cellPadding="10"
                    width="100%"
                >

                    <thead>

                    <tr>

                        <th>ID</th>
                        <th>Name</th>
                        <th>Code</th>
                        <th>Credits</th>
                        <th>Semester</th>
                        <th>Status</th>

                    </tr>

                    </thead>

                    <tbody>

                    {courses.map(course => (

                        <tr key={course.id}>

                            <td>{course.id}</td>

                            <td>
                                {course.courseName}
                            </td>

                            <td>
                                {course.courseCode}
                            </td>

                            <td>
                                {course.credits}
                            </td>

                            <td>
                                {course.semester}
                            </td>

                            <td>
                                {course.status}
                            </td>

                        </tr>

                    ))}

                    </tbody>

                </table>

            )}

        </div>
    );
}

export default Courses;
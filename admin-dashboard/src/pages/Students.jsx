import { useEffect, useState } from "react";
import API from "../services/api";

function Students() {

    const [students, setStudents] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchStudents = async () => {

        try {

            const response = await API.get("/students");

            console.log(
                "Full Response:",
                response
            );

            console.log(
                "Response Data:",
                response.data
            );

            console.log(
                "Type:",
                typeof response.data
            );

            console.log(
                "Is Array:",
                Array.isArray(response.data)
            );

            // Ensure students always stays an array
            if (Array.isArray(response.data)) {

                setStudents(
                    response.data
                );

            } else {

                console.log(
                    "Received non-array data"
                );

                setStudents([]);
            }

        } catch (error) {

            console.log(
                "API Error:",
                error
            );

            console.log(
                "Backend Response:",
                error.response?.data
            );

            alert(
                "Failed to load students"
            );

        } finally {

            setLoading(false);
        }
    };

    useEffect(() => {

        fetchStudents();

    }, []);

    if (loading) {

        return (

            <div
                style={{
                    padding: "20px"
                }}
            >
                Loading students...
            </div>
        );
    }

    return (

        <div
            style={{
                padding: "20px"
            }}
        >

            <h2>
                Student List
            </h2>

            {students.length === 0 ? (

                <p>
                    No students found
                </p>

            ) : (

                <table
                    border="1"
                    cellPadding="10"
                    cellSpacing="0"
                    width="100%"
                >

                    <thead>

                    <tr>

                        <th>ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Roll Number</th>
                        <th>Semester</th>

                    </tr>

                    </thead>

                    <tbody>

                    {students.map((student) => (

                        <tr
                            key={student.id}
                        >

                            <td>
                                {student.id}
                            </td>

                            <td>
                                {student.firstName}
                            </td>

                            <td>
                                {student.lastName}
                            </td>

                            <td>
                                {student.email}
                            </td>

                            <td>
                                {student.rollNumber}
                            </td>

                            <td>
                                {student.semester}
                            </td>

                        </tr>

                    ))}

                    </tbody>

                </table>

            )}

        </div>
    );
}

export default Students;
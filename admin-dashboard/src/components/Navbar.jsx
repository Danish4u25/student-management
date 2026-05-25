function Navbar() {

    const username =
        localStorage.getItem(
            "username"
        );

    return (

        <div
            style={{
                padding: "15px",
                background: "#eee"
            }}
        >

            Welcome, {username}

        </div>
    );
}

export default Navbar;
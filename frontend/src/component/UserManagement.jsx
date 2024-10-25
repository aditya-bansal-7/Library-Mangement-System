import React, { useEffect, useState } from "react";
import axios from "axios";
// import { PencilIcon } from "@heroicons/react/solid"; // Import Heroicons for edit icon

const UserManagement = () => {
    const [users, setUsers] = useState([]);
    const [editFineId, setEditFineId] = useState(null); // Track which user is being edited
    const [fineInput, setFineInput] = useState(0); // Track fine input for editing

    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = async () => {
        try {
            const response = await axios.get("http://localhost:8008/user/allUsers");
            setUsers(response.data);
            console.log(response.data);
        } catch (error) {
            console.error("Error fetching users:", error);
        }
    };

    const toggleUserStatus = async (userId, isActive) => {
        try {
            const endpoint = isActive ? `http://localhost:8008/user/blockUser/${userId}` : `http://localhost:8008/user/unblockUser/${userId}`;
            await axios.put(endpoint);
            fetchUsers(); // Refresh the list
        } catch (error) {
            console.error("Error updating user status:", error);
        }
    };

    const handleEditFine = (user) => {
        setEditFineId(user.userId);
        setFineInput(user.fine);
    };

    const handleUpdateFine = async (userId) => {
        try {
            await axios.put(`http://localhost:8008/user/updateFine/${userId}`, { fine: fineInput });
            setEditFineId(null); // Exit edit mode after updating
            fetchUsers(); // Refresh the list to show updated fine
        } catch (error) {
            console.error("Error updating fine:", error);
        }
    };

    return (
        <div className="container mx-auto px-4 py-6">
            <h2 className="text-2xl font-bold mb-4 text-center">User Management</h2>
            <table className="w-full border-collapse text-center shadow-lg">
                <thead>
                    <tr>
                        <th className="border-2 border-gray-300 bg-gray-100 px-4 py-4">Name</th>
                        <th className="border-2 border-gray-300 bg-gray-100 px-4 py-4">Email</th>
                        <th className="border-2 border-gray-300 bg-gray-100 px-4 py-4">Status</th>
                        <th className="border-2 border-gray-300 bg-gray-100 px-4 py-4">Fine</th>
                        <th className="border-2 border-gray-300 bg-gray-100 px-4 py-4">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map((user) => (
                        !user.admin && (
                            <tr key={user.userId} className="border-2 border-gray-300 bg-white">
                                <td className="px-4 py-4 text-gray-800">{user.firstName} {user.lastName}</td>
                                <td className="px-4 py-4 text-gray-800">{user.userEmail}</td>
                                <td className="px-4 py-4 text-gray-800">{user.active ? "Active" : "Blocked"}</td>
                                <td className="px-4 py-4 text-gray-800">
                                    {editFineId === user.userId ? (
                                        <input
                                            type="number"
                                            value={fineInput}
                                            onChange={(e) => setFineInput(parseFloat(e.target.value))}
                                            className="w-20 px-2 py-1 border rounded-md text-center focus:outline-none"
                                            onBlur={() => handleUpdateFine(user.userId)} // Update fine on blur
                                        />
                                    ) : (
                                        <span className="flex items-center justify-center">
                                            ${user.fine.toFixed(2)}

                                            <div className="w-5 h-5 text-blue-600 ml-2 cursor-pointer hover:text-blue-800"
                                                onClick={() => handleEditFine(user)}>Edit</div>
                                            {/* <PencilIcon
                                                className="w-5 h-5 text-blue-600 ml-2 cursor-pointer hover:text-blue-800"
                                                onClick={() => handleEditFine(user)}
                                            /> */}
                                        </span>
                                    )}
                                </td>
                                <td className="px-4 py-4 text-gray-800">
                                    <button
                                        onClick={() => toggleUserStatus(user.userId, user.active)}
                                        className={`inline-flex items-center px-4 py-2 text-sm font-medium rounded-md shadow-sm text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2`}
                                    >
                                        {user.active ? "Block" : "Unblock"}
                                    </button>
                                </td>
                            </tr>
                        )
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default UserManagement;

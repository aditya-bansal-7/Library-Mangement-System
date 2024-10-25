import React, { useEffect, useState } from "react";
import axios from "axios";

const UserManagement = () => {
    const [users, setUsers] = useState([]);
    const [editFineId, setEditFineId] = useState(null);
    const [fineInput, setFineInput] = useState(0);
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState('');
    const [statusFilter, setStatusFilter] = useState(""); 
    const [fineFilter, setFineFilter] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10); 
    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = async () => {
        setLoading(true);
        try {
            const response = await axios.get("http://localhost:8008/user/allUsers");
            setUsers(response.data);
        } catch (error) {
            console.error("Error fetching users:", error);
        }
        setLoading(false);
    };

    const toggleUserStatus = async (userId, isActive) => {
        const confirmed = window.confirm(`Are you sure you want to ${isActive ? 'block' : 'unblock'} this user?`);
        if (!confirmed) return;
        try {
            const endpoint = isActive
                ? `http://localhost:8008/user/blockUser/${userId}`
                : `http://localhost:8008/user/unblockUser/${userId}`;
            await axios.put(endpoint);
            fetchUsers();
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
            setEditFineId(null);
            fetchUsers();
        } catch (error) {
            console.error("Error updating fine:", error);
        }
    };

    const filteredUsers = users.filter(user => {
        const matchesSearchTerm = (user.firstName + ' ' + user.lastName).toLowerCase().includes(searchTerm.toLowerCase()) ||
            user.userEmail.toLowerCase().includes(searchTerm.toLowerCase());
        
        const matchesStatusFilter = 
            !statusFilter || (statusFilter === "active" && user.active) || (statusFilter === "blocked" && !user.active);
        
        const matchesFineFilter = 
            !fineFilter || (fineFilter === "withFine" && user.fine > 0) || (fineFilter === "withoutFine" && user.fine === 0);

        return matchesSearchTerm && matchesStatusFilter && matchesFineFilter;
    });

    // Paginated users
    const indexOfLastUser = currentPage * itemsPerPage;
    const indexOfFirstUser = indexOfLastUser - itemsPerPage;
    const currentUsers = filteredUsers.slice(indexOfFirstUser, indexOfLastUser);

    const totalPages = Math.ceil(filteredUsers.length / itemsPerPage);

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const handleItemsPerPageChange = (e) => {
        setItemsPerPage(parseInt(e.target.value, 10));
        setCurrentPage(1); 
    };

    return (
        <div className="container mx-auto px-4 py-6">
            <h2 className="text-3xl font-bold mb-8 text-center text-gray-700">User Management</h2>

            <div className="mb-6 flex flex-wrap gap-4">
                <input
                    type="text"
                    placeholder="Search users by name or email..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="flex-grow p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
                />

                <select
                    value={statusFilter}
                    onChange={(e) => setStatusFilter(e.target.value)}
                    className="p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
                >
                    <option value="">All Statuses</option>
                    <option value="active">Active</option>
                    <option value="blocked">Blocked</option>
                </select>

                <select
                    value={fineFilter}
                    onChange={(e) => setFineFilter(e.target.value)}
                    className="p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
                >
                    <option value="">All Fines</option>
                    <option value="withFine">With Fine</option>
                    <option value="withoutFine">Without Fine</option>
                </select>

                <select
                    value={itemsPerPage}
                    onChange={handleItemsPerPageChange}
                    className="p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
                >
                    <option value={10}>10</option>
                    <option value={20}>20</option>
                    <option value={50}>50</option>
                    <option value={100}>100</option>
                </select>
            </div>

            {loading ? (
                <div className="flex justify-center items-center">
                    <p className="text-gray-500">Loading users...</p>
                </div>
            ) : (
                <div className="overflow-x-auto">
                    <table className="min-w-full border-collapse text-center shadow-lg">
                        <thead>
                            <tr>
                                <th className="border px-4 py-3 bg-gray-100 text-gray-700 font-semibold">Name</th>
                                <th className="border px-4 py-3 bg-gray-100 text-gray-700 font-semibold">Email</th>
                                <th className="border px-4 py-3 bg-gray-100 text-gray-700 font-semibold">Status</th>
                                <th className="border px-4 py-3 bg-gray-100 text-gray-700 font-semibold">Fine</th>
                                <th className="border px-4 py-3 bg-gray-100 text-gray-700 font-semibold">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {currentUsers.map(user => (
                                !user.admin && (
                                    <tr key={user.userId} className="hover:bg-gray-50">
                                        <td className="border px-4 py-3 text-gray-800">
                                            {user.firstName} {user.lastName}
                                        </td>
                                        <td className="border px-4 py-3 text-gray-800">{user.userEmail}</td>
                                        <td className="border px-4 py-3">
                                            <span className={`px-2 py-1 text-sm font-medium rounded ${
                                                user.active ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'
                                            }`}>
                                                {user.active ? "Active" : "Blocked"}
                                            </span>
                                        </td>
                                        <td className="border px-4 py-3">
                                            {editFineId === user.userId ? (
                                                <input
                                                    type="number"
                                                    value={fineInput}
                                                    onChange={(e) => setFineInput(parseFloat(e.target.value))}
                                                    className="w-20 px-2 py-1 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                                                    onBlur={() => handleUpdateFine(user.userId)}
                                                />
                                            ) : (
                                                <div className="flex justify-center items-center">
                                                    ${user.fine.toFixed(2)}
                                                    <button
                                                        onClick={() => handleEditFine(user)}
                                                        className="ml-2 text-blue-600 hover:text-blue-800"
                                                    >
                                                        Edit
                                                    </button>
                                                </div>
                                            )}
                                        </td>
                                        <td className="border px-4 py-3">
                                            <button
                                                onClick={() => toggleUserStatus(user.userId, user.active)}
                                                className={`px-4 py-2 text-sm font-medium rounded shadow-sm text-white ${
                                                    user.active ? 'bg-red-500 hover:bg-red-600' : 'bg-green-500 hover:bg-green-600'
                                                } transition duration-200`}
                                            >
                                                {user.active ? "Block" : "Unblock"}
                                            </button>
                                        </td>
                                    </tr>
                                )
                            ))}
                        </tbody>
                    </table>

                    {filteredUsers.length === 0 && !loading && (
                        <p className="text-center text-gray-500 mt-4">No users found.</p>
                    )}
                </div>
            )}

            <div className="flex justify-between items-center mt-4">
                <span className="text-gray-700">
                    Page {currentPage} of {totalPages}
                </span>
                <div className="flex space-x-2">
                    {Array.from({ length: totalPages }, (_, index) => (
                        <button
                            key={index}
                            onClick={() => handlePageChange(index + 1)}
                            className={`px-3 py-1 rounded ${currentPage === index + 1 ? 'bg-blue-500 text-white' : 'bg-gray-300 text-gray-700 hover:bg-gray-400'}`}
                        >
                            {index + 1}
                        </button>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default UserManagement;

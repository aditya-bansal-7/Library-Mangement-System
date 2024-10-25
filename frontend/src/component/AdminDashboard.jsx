import React from 'react';
import { Link, Outlet } from 'react-router-dom';

const AdminDashboard = () => {
    return (
        <div className="flex">            
            {/* Main content area */}
            <div className="flex-1">
                <Outlet />
            </div>
        </div>
    );
};

export default AdminDashboard;

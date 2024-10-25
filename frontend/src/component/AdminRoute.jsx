import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import AdminDashboard from './AdminDashboard';

const AdminRoute = () => {
    const user = JSON.parse(localStorage.getItem('user'));

    if (user && user.admin) {
        return <AdminDashboard />;
    } else {
        return <Navigate to="/login" />;
    }
};

export default AdminRoute;
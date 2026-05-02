import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import EmployeeList from './pages/EmployeeList';
import EmployeeForm from './pages/EmployeeForm';
import DepartmentList from './pages/DepartmentList';
import DepartmentForm from './pages/DepartmentForm';
import Profile from './pages/Profile';
import Register from './pages/Register';

// Protected Route component
const ProtectedRoute = ({ children }) => {
    const { token, loading } = useAuth();
    if (loading) return <div className="flex items-center justify-center min-h-screen">Loading...</div>;
    return token ? children : <Navigate to="/login" />;
};

const App = () => {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/login" element={<Login />} />
                    <Route path="/dashboard" element={
                        <ProtectedRoute>
                            <Dashboard />
                        </ProtectedRoute>
                    } />
                    <Route path="/employees" element={
                        <ProtectedRoute>
                            <EmployeeList />
                        </ProtectedRoute>
                    } />
                    <Route path="/employees/add" element={
                        <ProtectedRoute>
                            <EmployeeForm />
                        </ProtectedRoute>
                    } />
                    <Route path="/employees/edit/:id" element={
                        <ProtectedRoute>
                            <EmployeeForm />
                        </ProtectedRoute>
                    } />
                    <Route path="/departments" element={
                        <ProtectedRoute>
                            <DepartmentList />
                        </ProtectedRoute>
                    } />
                    <Route path="/departments/add" element={
                        <ProtectedRoute>
                            <DepartmentForm />
                        </ProtectedRoute>
                    } />
                    <Route path="/departments/edit/:id" element={
                        <ProtectedRoute>
                            <DepagrtmentForm />
                        </ProtectedRoute>
                    } />
                    <Route path="/profile" element={
                        <ProtectedRoute>
                            <Profile />
                        </ProtectedRoute>
                    } />
                    <Route path="/" element={<Navigate to="/login" />} />
                    <Route path="/register" element={<Register />} />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
};

export default App;
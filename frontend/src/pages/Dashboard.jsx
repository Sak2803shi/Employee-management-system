import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <div className="min-h-screen bg-gray-100">
            {/* Navbar */}
            <nav className="bg-blue-600 text-white px-6 py-4 flex justify-between items-center">
                <h1 className="text-xl font-bold">Employee Management System</h1>
                <div className="flex items-center gap-4">
                    <span>Welcome, {user?.username}!</span>
                    <span className="bg-blue-800 px-2 py-1 rounded text-sm">{user?.role}</span>
                    <button
                        onClick={handleLogout}
                        className="bg-red-500 px-4 py-1 rounded hover:bg-red-600"
                    >
                        Logout
                    </button>
                </div>
            </nav>

            {/* Dashboard Cards */}
            <div className="p-6">
                <h2 className="text-2xl font-bold text-gray-800 mb-6">Dashboard</h2>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <div
                        onClick={() => navigate('/employees')}
                        className="bg-white p-6 rounded-lg shadow cursor-pointer hover:shadow-md transition">
                        <h3 className="text-lg font-bold text-blue-600">Employees</h3>
                        <p className="text-gray-500 mt-2">Manage all employees</p>
                        <p className="text-3xl font-bold text-gray-800 mt-4">→</p>
                    </div>
                    <div
                        onClick={() => navigate('/departments')}
                        className="bg-white p-6 rounded-lg shadow cursor-pointer hover:shadow-md transition">
                        <h3 className="text-lg font-bold text-green-600">Departments</h3>
                        <p className="text-gray-500 mt-2">Manage all departments</p>
                        <p className="text-3xl font-bold text-gray-800 mt-4">→</p>
                    </div>
                    <div className="bg-white p-6 rounded-lg shadow">
                        <h3 className="text-lg font-bold text-purple-600">My Profile</h3>
                        <p className="text-gray-500 mt-2">View your profile</p>
                        <p className="text-3xl font-bold text-gray-800 mt-4">
                            {user?.username}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
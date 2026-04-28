import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { departmentService } from '../services/api';

const DepartmentList = () => {
    const [departments, setDepartments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchDepartments();
    }, []);

    const fetchDepartments = async () => {
        try {
            setLoading(true);
            const response = await departmentService.getAll();
            setDepartments(response.data);
        } catch (err) {
            setError('Failed to fetch departments');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this department?')) {
            try {
                await departmentService.delete(id);
                fetchDepartments();
            } catch (err) {
                setError('Failed to delete department');
            }
        }
    };

    if (loading) return (
        <div className="flex items-center justify-center min-h-screen">
            <p className="text-gray-500 text-lg">Loading...</p>
        </div>
    );

    return (
        <div className="min-h-screen bg-gray-100">
            {/* Navbar */}
            <nav className="bg-blue-600 text-white px-4 py-4 flex flex-wrap justify-between items-center gap-2">
                <h1 className="text-xl font-bold">Employee Management System</h1>
                <button
                    onClick={() => navigate('/dashboard')}
                    className="bg-blue-800 px-4 py-1 rounded hover:bg-blue-900"
                >
                    Back to Dashboard
                </button>
            </nav>

            <div className="p-6">
                {/* Header */}
                <div className="flex justify-between items-center mb-6">
                    <h2 className="text-2xl font-bold text-gray-800">Departments</h2>
                    <button
                        onClick={() => navigate('/departments/add')}
                        className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600 font-bold"
                    >
                        + Add Department
                    </button>
                </div>

                {error && (
                    <div className="bg-red-100 text-red-700 p-3 rounded mb-4">
                        {error}
                    </div>
                )}

                {/* Cards Grid */}
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {departments.length === 0 ? (
                        <p className="text-gray-500">No departments found</p>
                    ) : (
                        departments.map((dept) => (
                            <div key={dept.id} className="bg-white rounded-lg shadow p-6">
                                <div className="flex justify-between items-start mb-4">
                                    <h3 className="text-lg font-bold text-blue-600">{dept.name}</h3>
                                    <span className="bg-gray-100 text-gray-600 px-2 py-1 rounded text-xs">
                                        {dept.employees?.length || 0} employees
                                    </span>
                                </div>
                                <p className="text-gray-500 text-sm mb-2">{dept.description}</p>
                                <p className="text-gray-400 text-xs mb-4">📍 {dept.location}</p>
                                <div className="flex gap-2">
                                    <button
                                        onClick={() => navigate(`/departments/edit/${dept.id}`)}
                                        className="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600 text-sm"
                                    >
                                        Edit
                                    </button>
                                    <button
                                        onClick={() => handleDelete(dept.id)}
                                        className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 text-sm"
                                    >
                                        Delete
                                    </button>
                                </div>
                            </div>
                        ))
                    )}
                </div>
            </div>
        </div>
    );
};

export default DepartmentList;
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { employeeService } from '../services/api';

const EmployeeList = () => {
    const [employees, setEmployees] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [search, setSearch] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchEmployees();
    }, []);

    const fetchEmployees = async () => {
        try {
            setLoading(true);
            const response = await employeeService.getAll();
            setEmployees(response.data);
        } catch (err) {
            setError('Failed to fetch employees');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this employee?')) {
            try {
                await employeeService.delete(id);
                fetchEmployees();
            } catch (err) {
                setError('Failed to delete employee');
            }
        }
    };

    const handleSearch = async (e) => {
        setSearch(e.target.value);
        if (e.target.value.trim() === '') {
            fetchEmployees();
        } else {
            try {
                const response = await employeeService.search(e.target.value);
                setEmployees(response.data.content);
            } catch (err) {
                setError('Search failed');
            }
        }
    };

    const handleExportCSV = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await fetch(
                'http://localhost:8081/api/employees/export/csv',
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'employees.csv';
            a.click();
            window.URL.revokeObjectURL(url);
        } catch (err) {
            setError('Failed to export CSV');
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
                    <h2 className="text-2xl font-bold text-gray-800">Employees</h2>
                    <div className="flex gap-2">
                        <button
                            onClick={handleExportCSV}
                            className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600 font-bold"
                        >
                            ⬇ Export CSV
                        </button>
                        <button
                            onClick={() => navigate('/employees/add')}
                            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 font-bold"
                        >
                            + Add Employee
                        </button>
                    </div>
                </div>

                {/* Search */}
                <div className="mb-4">
                    <input
                        type="text"
                        value={search}
                        onChange={handleSearch}
                        placeholder="Search employees by name or job title..."
                        className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
                    />
                </div>

                {error && (
                    <div className="bg-red-100 text-red-700 p-3 rounded mb-4">
                        {error}
                    </div>
                )}

                {/* Table */}
                <div className="bg-white rounded-lg shadow overflow-hidden overflow-x-auto">
                    <table className="w-full min-w-max">
                        <thead className="bg-gray-50 border-b">
                            <tr>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">ID</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Name</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Email</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Job Title</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Department</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Actions</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-gray-200">
                            {employees.length === 0 ? (
                                <tr>
                                    <td colSpan="6" className="px-6 py-4 text-center text-gray-500">
                                        No employees found
                                    </td>
                                </tr>
                            ) : (
                                employees.map((emp) => (
                                    <tr key={emp.id} className="hover:bg-gray-50">
                                        <td className="px-6 py-4 text-sm text-gray-900">{emp.id}</td>
                                        <td className="px-6 py-4 text-sm font-medium text-gray-900">
                                            {emp.firstName} {emp.lastName}
                                        </td>
                                        <td className="px-6 py-4 text-sm text-gray-500">{emp.email}</td>
                                        <td className="px-6 py-4 text-sm text-gray-500">{emp.jobTitle}</td>
                                        <td className="px-6 py-4 text-sm text-gray-500">
                                            <span className="bg-blue-100 text-blue-800 px-2 py-1 rounded text-xs">
                                                {emp.departmentName || 'N/A'}
                                            </span>
                                        </td>
                                        <td className="px-6 py-4 text-sm">
                                            <button
                                                onClick={() => navigate(`/employees/edit/${emp.id}`)}
                                                className="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600 mr-2"
                                            >
                                                Edit
                                            </button>
                                            <button
                                                onClick={() => handleDelete(emp.id)}
                                                className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                                            >
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                ))
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default EmployeeList;
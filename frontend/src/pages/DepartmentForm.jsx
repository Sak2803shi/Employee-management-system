import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { departmentService } from '../services/api';

const DepartmentForm = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const isEdit = !!id;

    const [formData, setFormData] = useState({
        name: '',
        description: '',
        location: '',
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    useEffect(() => {
        if (isEdit) fetchDepartment();
    }, []);

    const fetchDepartment = async () => {
        try {
            const response = await departmentService.getById(id);
            const dept = response.data;
            setFormData({
                name: dept.name,
                description: dept.description || '',
                location: dept.location || '',
            });
        } catch (err) {
            setError('Failed to fetch department');
        }
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');
        try {
            if (isEdit) {
                await departmentService.update(id, formData);
            } else {
                await departmentService.create(formData);
            }
            navigate('/departments');
        } catch (err) {
            setError(err.response?.data?.message || 'Something went wrong');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-gray-100">
            {/* Navbar */}
            <nav className="bg-blue-600 text-white px-4 py-4 flex flex-wrap justify-between items-center gap-2">
                <h1 className="text-xl font-bold">Employee Management System</h1>
                <button
                    onClick={() => navigate('/departments')}
                    className="bg-blue-800 px-4 py-1 rounded hover:bg-blue-900"
                >
                    Back to Departments
                </button>
            </nav>

            <div className="p-6 max-w-lg mx-auto">
                <h2 className="text-2xl font-bold text-gray-800 mb-6">
                    {isEdit ? 'Edit Department' : 'Add Department'}
                </h2>

                {error && (
                    <div className="bg-red-100 text-red-700 p-3 rounded mb-4">
                        {error}
                    </div>
                )}

                <div className="bg-white rounded-lg shadow p-6">
                    <div className="mb-4">
                        <label className="block text-gray-700 text-sm font-bold mb-2">
                            Department Name
                        </label>
                        <input
                            type="text"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
                            required
                        />
                    </div>

                    <div className="mb-4">
                        <label className="block text-gray-700 text-sm font-bold mb-2">
                            Description
                        </label>
                        <textarea
                            name="description"
                            value={formData.description}
                            onChange={handleChange}
                            rows="3"
                            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
                        />
                    </div>

                    <div className="mb-6">
                        <label className="block text-gray-700 text-sm font-bold mb-2">
                            Location
                        </label>
                        <input
                            type="text"
                            name="location"
                            value={formData.location}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
                        />
                    </div>

                    <div className="flex gap-4">
                        <button
                            onClick={handleSubmit}
                            disabled={loading}
                            className="bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600 disabled:opacity-50 font-bold"
                        >
                            {loading ? 'Saving...' : isEdit ? 'Update Department' : 'Add Department'}
                        </button>
                        <button
                            onClick={() => navigate('/departments')}
                            className="bg-gray-500 text-white px-6 py-2 rounded hover:bg-gray-600"
                        >
                            Cancel
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default DepartmentForm;
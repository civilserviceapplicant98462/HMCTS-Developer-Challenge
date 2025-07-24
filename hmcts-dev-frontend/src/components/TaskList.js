import React, { useState, useEffect } from 'react'
import TaskForm from './TaskForm'
import Task from './Task'
import {
    getTasks as getTasksApi,
    createTask as createTaskApi,
    updateTask as updateTaskApi,
    deleteTask as deleteTaskApi
} from './provider'

function TaskList() {
    const [tasks, setTasks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchTasks();
    }, []);

    const fetchTasks = async () => {
        setLoading(true);
        try {
            const data = await getTasksApi();
            setTasks(data);
        } catch (err) {
            setError('Failed to load tasks');
        }
        setLoading(false);
    };

    const addTask = async (task) => {
        try {
            const newTask = await createTaskApi(task);
            setTasks(prev => [newTask, ...prev]);
        } catch (err) {
            setError('Failed to add task');
        }
    };

    const removeTask = async (id) => {
        try {
            await deleteTaskApi(id);
            setTasks(prev => prev.filter(task => task.id !== id));
        } catch (err) {
            setError('Failed to delete task');
        }
    };

    const updateTask = async (taskId, newValue) => {
    try {
        const updated = await updateTaskApi({ ...newValue, id: taskId });
        setTasks(prev => prev.map(item => (item.id === taskId ? updated : item)));
    } catch (err) {
        setError('Failed to update task');
    }
};

    const completeTask = async (id) => {
    const task = tasks.find(t => t.id === id);
    if (!task) return;
    try {
        const updated = await updateTaskApi({ ...task, completed: !task.completed });
        setTasks(prev => prev.map(item => (item.id === id ? updated : item)));
    } catch (err) {
        setError('Failed to complete task');
    }
};

    useEffect(() => {
        if (error) {
            const timer = setTimeout(() => setError(null), 3000);
            return () => clearTimeout(timer);
        }
    }, [error]);

    return (
        <div>
            {error && <div className="error">{error}</div>}
            <TaskForm onSubmit={addTask} />
            {loading ? (
                <div>Loading...</div>
            ) : (
                <Task
                    tasks={tasks}
                    completeTask={completeTask}
                    removeTask={removeTask}
                    updateTask={updateTask}
                />
            )}
        </div>
    )
}

export default TaskList
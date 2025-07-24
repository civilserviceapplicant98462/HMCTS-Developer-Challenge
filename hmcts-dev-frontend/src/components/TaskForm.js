import React, { useState, useEffect, useRef } from 'react'

const IMPORTANCE_OPTIONS = ['HIGH', 'MEDIUM', 'LOW'];

function TaskForm(props) {
    const [title, setTitle] = useState(props.edit ? props.edit.title : '');
    const [taskDescription, setTaskDescription] = useState(props.edit ? props.edit.taskDescription : '');
    const [importance, setImportance] = useState(props.edit ? props.edit.importance : 'MEDIUM');
    const [due, setDue] = useState(props.edit ? (props.edit.due ? props.edit.due.substring(0, 16) : '') : '');
    // HTML datetime-local expects 'YYYY-MM-DDTHH:mm'

    const inputRef = useRef(null);

    useEffect(() => {
        inputRef.current.focus();
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();

        props.onSubmit({
            id: props.edit && props.edit.id ? props.edit.id : Math.floor(Math.random() * 10000),
            title,
            taskDescription,
            importance,
            due: due ? new Date(due).toISOString() : null,
            completed: props.edit && typeof props.edit.completed === 'boolean' ? props.edit.completed : false,
        });

        setTitle('');
        setTaskDescription('');
        setImportance('MEDIUM');
        setDue('');
    }

    return (
        <form className='todo-form' onSubmit={handleSubmit}>
            <input
                type='text'
                name='title'
                className={`todo-input${props.edit ? ' edit' : ''}`}
                placeholder='Task title'
                value={title}
                onChange={e => setTitle(e.target.value)}
                ref={inputRef}
                required
            />
            <textarea
                name='taskDescription'
                className='todo-input-desc'
                placeholder='Task description'
                value={taskDescription}
                onChange={e => setTaskDescription(e.target.value)}
            />
            <select
                name='importance'
                className='todo-input-select'
                value={importance}
                onChange={e => setImportance(e.target.value)}
            >
                {IMPORTANCE_OPTIONS.map(opt => (
                    <option key={opt} value={opt}>{opt}</option>
                ))}
            </select>
            <input
                type='datetime-local'
                name='due'
                className='todo-input-date'
                value={due}
                onChange={e => setDue(e.target.value)}
            />
            <button className={`todo-button${props.edit ? ' edit' : ''}`} type='submit'>
                {props.edit ? 'Update' : 'Add'}
            </button>
        </form>
    )
}

export default TaskForm
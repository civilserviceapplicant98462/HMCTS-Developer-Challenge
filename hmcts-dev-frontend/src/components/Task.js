import React, { useState } from 'react'
import TaskForm from './TaskForm'
import { TiEdit } from 'react-icons/ti'
import { RiCloseCircleLine } from 'react-icons/ri';

function Task({ tasks, completeTask, removeTask, updateTask }) {
    const [edit, setEdit] = useState({
        id: null,
        title: '',
        taskDescription: '',
        importance: 'MEDIUM',
        due: '',
    });

    const submitUpdate = value => {
        updateTask(edit.id, value);
        setEdit({
            id: null,
            title: '',
            taskDescription: '',
            importance: 'MEDIUM',
            due: '',
        });
    }

    if (edit.id) {
        return (
            <TaskForm
                edit={edit}
                onSubmit={submitUpdate}
            />
        );
    }

    return tasks.map((task, index) => (
        <div
            className={task.completed ? 'todo-row complete' : 'todo-row'}
            key={task.id}
            onClick={() => completeTask(task.id)}
        >
            <div >
                <strong>{task.title}</strong>
                <div>{task.taskDescription}</div>
                <div>
                    <span className={`importance ${task.importance.toLowerCase()}`}>
                        {task.importance}
                    </span>
                    {task.due && (
                        <span className="due-date">
                            {' | Due: ' + new Date(task.due).toLocaleString()}
                        </span>
                    )}
                </div>
            </div>
            <div className='icons'>
                <RiCloseCircleLine
                    onClick={() => removeTask(task.id)}
                    className='delete-icon'
                />
                <TiEdit
                    onClick={() =>
                        setEdit({
                            id: task.id,
                            title: task.title,
                            taskDescription: task.taskDescription,
                            importance: task.importance,
                            due: task.due,
                        })
                    }
                    className='edit-icon'
                />
            </div>
        </div>
    ));
}

export default Task
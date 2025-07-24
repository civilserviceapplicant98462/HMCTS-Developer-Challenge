
const BACKEND_URL = 'http://localhost:8090'

export async function getTask() {
  const response = await fetch(BACKEND_URL + '/task');
  if (!response.ok) throw new Error('Failed to fetch task');
  return response.json();
}

export async function getTasks() {
  console.log('Fetching tasks from backend...');
  console.log('Backend URL:', BACKEND_URL + '/tasks');
  const response = await fetch(BACKEND_URL + '/tasks');
  if (!response.ok) throw new Error('Failed to fetch tasks');
  return response.json();
}

export async function createTask(task) {
  const response = await fetch(BACKEND_URL + '/task', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(task),
  });
  if (!response.ok) throw new Error('Failed to create task');
  return response.json();
}

export async function updateTask(task) {
  const response = await fetch(BACKEND_URL + '/task', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(task),
  });
  if (!response.ok) throw new Error('Failed to update task');
  return response.json();
}

export async function deleteTask(id) {
  const response = await fetch(BACKEND_URL + '/task/' + id, {
    method: 'DELETE',
  });
  if (!response.ok) throw new Error('Failed to delete task');
  return response.json();
}
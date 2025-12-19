const BASE = process.env.NEXT_PUBLIC_API_BASE_URL || "http://localhost:8080";

export function getToken(): string | null {
    if (typeof window === "undefined") return null;
    return localStorage.getItem("accessToken");
}

export function setToken(token: string) {
    localStorage.setItem("accessToken", token);
}

export function clearToken() {
    localStorage.removeItem("accessToken");
}

export async function api<T>(path: string, options: RequestInit = {}): Promise<T> {
    const token = getToken();

    const res = await fetch(`${BASE}${path}`, {
        ...options,
        headers: {
            "Content-Type": "application/json",

            ...(options.headers || {}),

            ...(token ? {Authorization: `Bearer ${token}`}: {}),
        },
    });

    if (!res.ok) {
        const text = await res.text();
        throw new Error(`${res.status} ${res.statusText} - ${text}`);
    }

    return (await res.json()) as T;

}
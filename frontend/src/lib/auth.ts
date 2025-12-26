

export function hasToken(): boolean {
    if (typeof window === "undefined") return false;
    const t = localStorage.getItem("accessToken");
    return !!t && t.length > 0;
}

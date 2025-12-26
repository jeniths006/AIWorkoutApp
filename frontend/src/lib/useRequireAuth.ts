"use client";


import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { hasToken } from "@/lib/auth";

export function useRequireAuth() {
    const router = useRouter();
    const [ready, setReady] = useState(false);

    useEffect(() => {
        if (!hasToken()) {
            router.replace("/login");
            return;
        }
        setReady(true);
    }, [router]);

    return { ready };
}

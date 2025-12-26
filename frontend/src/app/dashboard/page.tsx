"use client";

/**
  ================================
  DASHBOARD PAGE
  ================================

  This is the main logged-in landing page.

 */

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { api, clearToken } from "@/lib/api";
import type { MeResponse } from "@/lib/types";
import { useRequireAuth } from "@/lib/useRequireAuth";

export default function DashboardPage() {
    const router = useRouter();


    const { ready } = useRequireAuth();
    const [me, setMe] = useState<MeResponse | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!ready) return;

        api<MeResponse>("/api/me")
            .then(setMe)
            .catch((err: unknown) => {
                const msg = err instanceof Error ? err.message : "Failed to load user";
                setError(msg);
            });
    }, [ready]);

    function logout() {
        clearToken();
        router.replace("/login");
    }

    if (!ready) {
        return (
            <main className="min-h-screen bg-slate-950 text-slate-100 flex items-center justify-center">
                <div className="text-sm text-slate-400">Checking session...</div>
            </main>
        );
    }

    /* ================================
       HERO SECTION BELOW
       ================================ */

    return (
        <main className="min-h-screen bg-slate-950 text-slate-100">

            <header className="border-b border-white/10">
                <div className="mx-auto max-w-6xl px-6 py-4 flex items-center justify-between">
                    {/* LEFT SIDE — APP BRANDING */}
                    <div>
                        <h1 className="text-lg font-semibold tracking-tight">
                            AIWorkoutApp
                        </h1>
                        <p className="text-xs text-slate-400">
                            Dashboard
                        </p>
                    </div>


                    <div className="flex items-center gap-3">

                        <button
                            onClick={logout}
                            className="
                                rounded-xl border border-white/10 bg-white/5 px-3 py-2
                                text-sm font-semibold
                                hover:bg-white/10
                                transition
                              "
                        >
                            Logout
                        </button>
                    </div>
                </div>
            </header>


            <section className="mx-auto max-w-6xl px-6 py-10">
                {/* HERO / INTRO */}
                <div className="mb-10">
                    <h2 className="text-2xl font-semibold tracking-tight">
                        Welcome 👋
                    </h2>
                    <p className="mt-2 text-slate-300 max-w-xl">
                        This is your training dashboard. From here you’ll manage workouts,
                        track progress, and view analytics.
                    </p>
                </div>


                <div className="grid gap-8 md:grid-cols-2">

                    <div className="rounded-2xl border border-white/10 bg-white/5 p-6">
                        <h3 className="text-sm font-semibold text-slate-200">
                            Current User
                        </h3>



                        {!me && !error && (
                            <div className="mt-4 space-y-3">
                                <div className="h-4 w-2/3 animate-pulse rounded bg-white/10" />
                                <div className="h-4 w-full animate-pulse rounded bg-white/10" />
                            </div>
                        )}


                        {error && (
                            <div className="mt-4 rounded-xl border border-red-500/30 bg-red-500/10 px-4 py-3 text-sm text-red-200">
                                {error}
                            </div>
                        )}


                        {me && (
                            <div className="mt-4 space-y-2 text-sm">
                                <div>
                                    <span className="text-slate-400">Email:</span>{" "}
                                    <span className="font-medium">{me.email}</span>
                                </div>
                                <div>
                                    <span className="text-slate-400">User ID:</span>{" "}
                                    <span className="font-mono text-xs text-slate-200">
                    {me.userId}
                  </span>
                                </div>
                            </div>
                        )}
                    </div>


                    <div className="rounded-2xl border border-white/10 bg-white/5 p-6">
                        <h3 className="text-sm font-semibold text-slate-200">
                            Quick Actions
                        </h3>
                        <p className="mt-1 text-xs text-slate-400">
                            Navigation shortcuts
                        </p>

                        <div className="mt-5 grid gap-4">

                            <button
                                onClick={() => router.push("/workouts")}
                                className="
                                  w-full rounded-xl bg-indigo-500 px-4 py-3
                                  text-sm font-semibold text-white
                                  shadow-lg shadow-indigo-500/20
                                  hover:bg-indigo-400
                                  transition
                                "
                            >
                                Go to Workouts
                            </button>


                            <button
                                onClick={() => router.push("/analytics")}
                                className="
                                    w-full rounded-xl border border-white/10 bg-white/5 px-4 py-3
                                    text-sm font-semibold
                                    hover:bg-white/10
                                    transition
                                    "
                            >
                                View Analytics
                            </button>
                        </div>

                        <p className="mt-4 text-xs text-slate-400">
                            These pages will be built next.
                        </p>
                    </div>
                </div>
            </section>
        </main>
    );
}

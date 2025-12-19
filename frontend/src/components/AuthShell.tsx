import React, {ReactNode} from "react";


export default function AuthShell({children }: {children: ReactNode}) {
    return (
        <main className="min-h-screen bg-gradient-to-b from-slate-950 via-slate-950 to-slate-900 text-slate-100">

            <div
                className="
                pointer-events-none
                absolute inset-0
                opacity-30
                [background-image:linear-gradient(to_right,rgba(255,255,255,.05)_1px,transparent_1px),linear-gradient(to_bottom,rgba(255,255,255,.05)_1px,transparent_1px)]
                [background-size:48px_48px]
                [mask-image:radial-gradient(ellipse_at_center,black_60%,transparent_100%)]
                "
            />

            <div
                className="
                pointer-events-none
                absolute left-1/2 top-24 -translate-x-1/2h-72 w-72rounded-full
                bg-indigo-500/20
                blur-3xl
                "
            />

            <div className="relative flex min-h-screen items-center justify-center p-6">
                <div className="w-full max-w-md">{children}</div>
            </div>


        </main>
    )
}
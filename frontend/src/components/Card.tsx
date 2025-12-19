import {ReactNode} from "react";

export function Card({children } : {children: React.ReactNode}) {
    return (
        <div className="rounded-2x1 border border-white/10 bg-white/5 shadow-x1 shadow-black/20 backdrop-blue">
            {children}
        </div>
    );
}

export function CardBody({children }: {children: React.ReactNode}) {
    return <div className="p-6 sm:p-7">{children}</div>
}
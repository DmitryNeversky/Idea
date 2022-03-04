import {animate, query, style, transition, trigger} from "@angular/animations";

export const routesAnimation =
    trigger('routesAnimation', [
        transition('* => *', [

            query(':enter, :leave', style({
                position: 'absolute', width: '92.4%',
            }), {optional: true}),

            query(':enter',
                [
                    style({opacity: 0})
                ],
                {optional: true}
            ),

            query(':leave',
                [
                    style({opacity: 1}),
                    animate('0.2s', style({opacity: 0}))
                ],
                {optional: true}
            ),

            query(':enter',
                [
                    style({opacity: 0}),
                    animate('0.2s', style({opacity: 1}))
                ],
                {optional: true}
            )

        ])
    ]);